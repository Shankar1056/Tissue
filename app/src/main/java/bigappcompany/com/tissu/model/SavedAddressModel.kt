package bigappcompany.com.tissu.model

/**
 * @author Samuel Robert <sam></sam>@spotsoon.com>
 * @created on 29 Jun 2017 at 11:31 AM
 */

class SavedAddressModel(
        val address_type_id: String,
        val address_type_name: String,
        val state_id: String,
        val state_name: String,
        val pincode: String,
        val address1: String,
        val address2: String,
        val city: String,
        val address_id: String,
        var isSelected: Boolean)
