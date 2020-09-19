package edu.uga.cs.fetchrewardsandroid

import android.os.Bundle
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.uga.cs.fetchrewardsexcercise.ViewModels.HiringInfoViewModel

class MainActivity : AppCompatActivity() {

    lateinit var expandableListView: ExpandableListView
    lateinit var customExpandableListAdapter: CustomExpandableListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        expandableListView =  findViewById(R.id.expandableListView)
        val viewModel = ViewModelProvider(this).get(HiringInfoViewModel::class.java)
        viewModel.fetchData()
        viewModel.getHiringInfoData().observe(this, Observer {
            // update UI
            customExpandableListAdapter = CustomExpandableListAdapter(this, it.keys.toList(), it)
            expandableListView.setAdapter(customExpandableListAdapter)

        })
    }
}
