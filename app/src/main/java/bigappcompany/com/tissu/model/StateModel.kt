package bigappcompany.com.tissu.model

/**
 * @author Samuel Robert <sam></sam>@spotsoon.com>
 * @created on 28 Jun 2017 at 7:15 PM
 */

class StateModel(id_state: String, name: String, state_code: String) {

    var id_state: String
        internal set
    var name: String
        internal set
    var state_code: String
        internal set

    init {
        this.id_state = id_state
        this.name = name
        this.state_code = state_code
    }
}
