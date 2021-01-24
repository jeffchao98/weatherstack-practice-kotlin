package com.scchao.wtrstkpractice.ui.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.scchao.wtrstkpractice.R
import com.scchao.wtrstkpractice.data.model.Weather
import com.scchao.wtrstkpractice.ui.adapter.GridAdapter
import com.scchao.wtrstkpractice.ui.model.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {
    var callback: OnItemClickListener? = null
    var editText: EditText? = null
    var gridView: GridView? = null
    fun setOnItemClickListener(callback: OnItemClickListener) {
        this.callback = callback
    }

    interface OnItemClickListener {
        fun onItemClick(weather: Weather)
    }

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_list, container, false)
        editText = root.findViewById(R.id.input_key)
        gridView = root.findViewById(R.id.grid_list)
        root.findViewById<ImageButton>(R.id.search_button).setOnClickListener {
            val inputText = editText?.text.toString() ?: ""
            if (!inputText.isEmpty()) {
                mainViewModel.search(editText?.text.toString())
            }
        }
        mainViewModel.preloadData().observe(this, dataObserver)
        mainViewModel.preparedData().observe(this, dataObserver)
        mainViewModel.modifiedData().observe(this, dataObserver)
        if (!cacheList.isEmpty()) {
            applyDataList(cacheList)
        } else {
            mainViewModel.preLoadKey()
        }
        return root
    }

    private var cacheList: MutableList<Weather> = mutableListOf()

    private val dataObserver = Observer<MutableList<Weather>> { itData ->
        applyDataList(itData)
    }

    private fun applyDataList(list: MutableList<Weather>) {
        context?.let { itContext ->
            Log.d("applyDataList", "data changed")
            cacheList = list
            val gridAdapter = GridAdapter(itContext, list)
            gridView?.adapter = gridAdapter
            gridView?.setOnItemClickListener { parent, view, position, id ->
                val selectData = list.get(position)
                System.out.println("${selectData.location.name} touched")
                callback?.onItemClick(selectData)
            }
            gridView?.setOnItemLongClickListener { parent, view, position, id ->
                val selectData = list.get(position)

                System.out.println("${selectData.location.name} long clicked")
                val builder: AlertDialog.Builder? = AlertDialog.Builder(itContext)
                builder?.setMessage(R.string.confirm_action)
                builder?.apply {
                    setPositiveButton(R.string.delete, DialogInterface.OnClickListener{dialog, which ->
                        mainViewModel.delete(selectData)
                    })
                    setNegativeButton(R.string.cancel, DialogInterface.OnClickListener{dialog, which ->

                    })
                }
                val dialog: AlertDialog? = builder?.create()
                dialog?.show()
                true
            }
        }
    }
}