package ru.stollmanSquad.orbiapp.adapters

import android.app.Activity
import java.util.ArrayList;
import android.content.Context;
import android.util.Log
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView
import android.widget.TextView;
import ru.stollmanSquad.orbiapp.R
import ru.stollmanSquad.orbiapp.models.Info
import android.widget.EditText




class InfoListAdapter(
        val ctx : Context,
        val objects : ArrayList<Info>
) : BaseAdapter() {

    private var lInflater: LayoutInflater = (ctx as Activity).layoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder : ViewHolder?
        val view : View?
        if (convertView == null) {
            holder = ViewHolder()
            view = lInflater.inflate(R.layout.info_list_item, parent, false)
            view.tag = holder
            holder.title = view.findViewById(R.id.InfoListTitleText)
            holder.description = view.findViewById(R.id.InfoListDescriptionText)
            holder.drawable = view.findViewById(R.id.InfoListImage)

        } else {
            holder = convertView.tag as ViewHolder;
            view = convertView
        }

        val item = objects[position]
        holder.position= position

        if(item.drawableId > 0){
            holder.drawable!!.visibility = View.VISIBLE
            holder.drawable!!.setImageDrawable(ctx.getDrawable(item.drawableId))
            holder.title!!.setText("")
            holder.description!!.setText("")
        }else{
            holder.drawable!!.visibility = View.GONE
            holder.title!!.setText(item.title)
            holder.description!!.setText(item.description)
        }

        return view!!
    }


    override fun getItem(position: Int): Any {
        return objects.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return objects.size
    }

}

private class ViewHolder {
    internal var title: TextView? = null
    internal var description: TextView? = null
    internal var drawable: ImageView? = null
    internal var position: Int = 0
}
