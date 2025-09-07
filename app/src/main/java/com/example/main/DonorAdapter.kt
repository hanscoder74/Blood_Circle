package com.example.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DonorAdapter(private val donorList: List<Donor>) :
    RecyclerView.Adapter<DonorAdapter.DonorViewHolder>() {

    class DonorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bloodGroup: TextView = itemView.findViewById(R.id.bloodGroup)
        val donorName: TextView = itemView.findViewById(R.id.donorName)
        val donorAddress: TextView = itemView.findViewById(R.id.donorAddress)
        val donorDetails: TextView = itemView.findViewById(R.id.donorDetails)
        val btnSendRequest: Button = itemView.findViewById(R.id.btnSendRequest)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_donor, parent, false)
        return DonorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DonorViewHolder, position: Int) {
        val donor = donorList[position]
        holder.bloodGroup.text = donor.bloodGroup
        holder.donorName.text = "Donor #${donor.id}" // Replace with actual name if added
        holder.donorAddress.text = donor.address
        holder.donorDetails.text = "Disease: ${donor.disease}\nAllergy: ${donor.allergy}\nTattoos: ${donor.tattoos}"

        holder.btnSendRequest.setOnClickListener {
            // TODO: Handle send request (call, SMS, Firebase push, etc.)
        }
    }

    override fun getItemCount(): Int = donorList.size
}
