package ru.stollmanSquad.orbiapp.views.adapters

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import ru.stollmanSquad.orbiapp.R
import ru.stollmanSquad.orbiapp.models.Challenge



class ChallengesListAdapter(
        val ctx : Context,
        val objects : List<Challenge>
) : BaseAdapter() {

    private var lInflater: LayoutInflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder : ViewHolder?
        val view : View?
        if (convertView == null) {
            holder = ViewHolder()
            view = lInflater.inflate(R.layout.list_item_layout, parent, false)
            view.tag = holder
            holder.title = view.findViewById(R.id.challenge_item_title)
            holder.description = view.findViewById(R.id.challenge_item_description)
            holder.money_pull = view.findViewById(R.id.challenge_money_pull)
            holder.time_expired = view.findViewById(R.id.challenge_time_expired)
        } else {
            holder = convertView.tag as ViewHolder
            view = convertView
        }

        val item = objects[position]
        holder.position= position

        holder.title!!.text = item.name
        holder.description!!.text = item.description
        holder.money_pull!!.text = item.money_pull.toString()
        holder.time_expired!!.text = item.time_expired

        view!!.setOnClickListener {}

        return view
    }


    override fun getItem(position: Int): Any {
        return objects[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return objects.size
    }

    private class ViewHolder {
        internal var title: TextView? = null
        internal var description: TextView? = null
        internal var money_pull : TextView? = null
        internal var time_expired: TextView? = null
        internal var position: Int = 0
    }

}