package bigappcompany.com.tissu.model

/**
 * @author Samuel Robert <sam></sam>@spotsoon.com>
 * @created on 28 Jun 2017 at 6:54 PM
 */

class AddressTypeModel(id_address_type: String, name: String) {

    var id_address_type: String
        internal set
    var name: String
        internal set

    init {
        this.id_address_type = id_address_type
        this.name = name
    }
}
