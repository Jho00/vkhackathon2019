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
        val objects : ArrayList<Challenge>
) : BaseAdapter() {

    private var lInflater: LayoutInflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view : View? = convertView
        if (view == null) {
            view = lInflater.inflate(R.layout.list_item_layout, parent, false)
        }

        val p = objects[position]

        view!!.findViewById<TextView>(R.id.challenge_item_title).text = p.name
        view.findViewById<TextView>(R.id.challenge_item_description).text = p.description
        view.findViewById<TextView>(R.id.challenge_money_pull).text = p.money_pull.toString()
        view.findViewById<TextView>(R.id.challenge_time_expired).text = p.time_expired

        view.setOnClickListener {}

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

}