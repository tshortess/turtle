import React, { Component } from "react";

class ItemRow extends Component {

    constructor(props) {
        super(props);
        this.state = {orderQuantity: "0"};

        this.handleChange = this.handleChange.bind(this);
    };

    handleChange(event) {
        this.setState({orderQuantity: event.target.value});
        let orderQuantity = event.target.value;
        this.props.updateOrderQuantity(this.props.candy.candyName, this.props.candy.sku, orderQuantity);
    };
    
    render() {
      return (
            <tr>
                <td>{this.props.candy.sku}</td>
                <td>{this.props.candy.candyName}</td>
                <td>{Number(this.props.candy.inStockAmount).toFixed(0)}</td>
                <td>{Number(this.props.candy.maxCapacity).toFixed(0)}</td>
                <td className="tableRowInputClass">
                  <input type="number" value={this.state.orderQuantity} min="0" max={Number(this.props.candy.maxCapacity) - Number(this.props.candy.inStockAmount)} onChange={this.handleChange} />
                </td>
            </tr>
      );
    }
}

export default ItemRow;