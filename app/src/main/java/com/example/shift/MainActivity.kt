package com.example.shift

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shift.databinding.ActivityMainBinding
import com.example.shift.adapter.BinAdapter
import com.example.shift.data.BinInfoModel
import com.example.shift.data.BinRepository
import com.example.shift.db.BinItem
import com.example.shift.db.BinItemDatabase
import com.example.shift.domain.BinInfo
import com.example.shift.presentation.BinSearchViewModel
import com.example.shift.presentation.SearchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val repository = BinRepository()
    private val adapter = BinAdapter()
    //нужна для отделения пользовательского запроса от запроса по нажатию на item, важный костыль
    private var isUserRequest = false
    private var lastBinNum: Long = 0
    private val binItemsList = ArrayList<BinItem>()
    private val database by lazy { BinItemDatabase.getInstance(this) }


    private val viewModel: BinSearchViewModel by viewModels {
        viewModelFactory {
            initializer {
                BinSearchViewModel(repository)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.state.observe(this, ::handleState)
        initRecyclerView()
        setBtnListener()
        turnOffNightMode()
    }

    private fun initRecyclerView() {
        adapter.setOnItemClickListener {

            //нужна для отделения пользовательского запроса от запроса по нажатию на item, важный костыль
            isUserRequest = false
            viewModel.loadData(binNum = it)
        }
        binding.mainRecyclerView.adapter = adapter
        binding.mainRecyclerView.layoutManager = getRecyclerViewManager()

        lifecycleScope.launch(Dispatchers.IO) {
            adapter.addItemsList(ArrayList(database.binItemDao().getItems()))
            scrollRecyclerViewToTop()
        }

    }

    private fun getRecyclerViewManager() = LinearLayoutManager(this@MainActivity).apply {
        reverseLayout = true
    }
    private fun turnOffNightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun setBtnListener() {
        binding.searchBtn.setOnClickListener {
            //нужна для отделения пользовательского запроса от запроса по нажатию на item, важный костыль
            isUserRequest = true
            trySearch()
        }
    }

    private fun trySearch(){
        try {
            val binNum = readInput()
            lastBinNum = binNum
            viewModel.loadData(binNum)
        } catch (ex: java.lang.NumberFormatException) {
            Log.e(TAG, ex.message.orEmpty())
            showErrorMsg(ex.message.orEmpty())
        }
    }

    private fun scrollRecyclerViewToTop(){
        binding.mainRecyclerView.smoothScrollToPosition(adapter.itemCount - 1)
    }


    private fun readInput() =  binding.editBin.text.toString().toLong()


    private fun handleState(state: SearchState) {
        when (state) {
            SearchState.Initial -> Unit
            SearchState.Loading -> Unit
            is SearchState.Content -> {
                handleBinInfoModel(state.binInfoModel)
            }
            is SearchState.Error -> showErrorMsg(state.text)
        }
    }

    private fun handleBinInfoModel(binInfoModel: BinInfoModel) {
        val binInfo = BinInfo(lastBinNum, binInfoModel)
        startInfoActivity(binInfo)
        if (isUserRequest) {
            val binItem = BinItem(
                binNum = lastBinNum,
                searchDate = getDateAndTime()
            )
            adapter.addItem(binItem)
            binItemsList.add(binItem)
            scrollRecyclerViewToTop()
            lifecycleScope.launch(Dispatchers.IO) {
                database.binItemDao().insertItem(binItem)
            }
        }
    }

    private fun showErrorMsg(msg: String) {
        Toast.makeText(
            this@MainActivity,
            "${getString(R.string.error)}: $msg",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun getDateAndTime(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val date = Date()
        return formatter.format(date)
    }

    private fun startInfoActivity(binInfo: BinInfo) {
        val intent = Intent(this@MainActivity, InfoActivity::class.java).apply {
            putExtra(BIN_INFO, binInfo)
        }
        startActivity(intent)
    }


}