package edu.uga.cs.fetchrewardsandroid

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import edu.uga.cs.fetchrewardsandroid.data_models.HiringInfo

class CustomExpandableListAdapter(var context:Context, var listTitles: List<Int>, val listDetails:
    Map<Int, List<HiringInfo>>): BaseExpandableListAdapter() {
    override fun getGroup(groupPosition: Int): Any {
        return listTitles[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        val listTitle = getGroup(groupPosition) as Int
        if (convertView == null) {
            val layoutInflater =
            this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_group, null)
        }
        val listTitleTextView = convertView!!.findViewById<TextView>(R.id.listTitle)
        listTitleTextView.setTypeface(null, Typeface.BOLD)
        listTitleTextView.text = "List id:$listTitle"
        return convertView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return listDetails[listTitles[groupPosition]]?.size ?: 0
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return listDetails[listTitles[groupPosition]]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        val hiringInfo = getChild(groupPosition,childPosition) as HiringInfo
        if(convertView == null){
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_item,null)
        }
        val nameTextView = convertView!!.findViewById<TextView>(R.id.expandedListItem1)
        nameTextView?.text = "Name: ${hiringInfo.name}"
        val idTextView = convertView.findViewById<TextView>(R.id.expandedListItem2)
        idTextView?.text = "Id: ${hiringInfo.id}"
        return convertView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return listTitles.size
    }

}