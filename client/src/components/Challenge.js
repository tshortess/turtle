import React, { Component } from "react";
import ItemRow from "../components/ItemRow";
import Banner from "../components/Banner";
import axios from "axios";

class Challenge extends Component {
  constructor(props){
    super(props);
    this.state = {
      lowInventory: [{
        sku: "",
        candyName: "",
        inStockAmount: "",
        maxCapacity: ""
      }],
      orderQuantities: [],
      totalPrice: "0",
      storeName: "Randy's Candies",
      lowStockThreshold: "25"
    };
  };
  
  componentDidMount() {
    axios.get("http://localhost:4567/full-stock", {
      headers: {
        storeName: this.state.storeName,
      }})
     .then(response => {
        const lowInventory = response.data.inventory;
        this.setState({
          lowInventory: lowInventory
        });
     })
  };
  
  getTotalPrice = () => {
    axios.post("http://localhost:4567/restock-cost", {
      storeName: this.state.storeName,
      orderQuantities: this.state.orderQuantities
    })
      .then(response => {
        const returnTotalPrice = response.data.totalPrice;
        this.setState({
          totalPrice: returnTotalPrice
        })
      })
  };

  getLowInventory = () => {
    axios.get("http://localhost:4567/low-stock", {
      headers: {
        storeName: this.state.storeName,
        lowStockThreshold: this.state.lowStockThreshold
      }})
     .then(response => {
        const lowInventory = response.data.inventory;
        this.setState({
          lowInventory: lowInventory
        });
        this.setState({
          orderQuantities: this.state.orderQuantities.filter(order => this.state.lowInventory.filter(candy => candy.sku === order.sku).length >= 1)
        })
     })
  };

  setReorderInventory = (inputCandyName, inputCandySku, orderQuantity) => {
    let orders = [...this.state.orderQuantities];
    let index = orders.findIndex(candy => candy.candyName === inputCandyName);
    if (index !== -1) {
      let order = { ...orders[index] };
      order["orderQuantity"] = orderQuantity;
      orders[index] = order;
    } else {
      let order = {
        sku: inputCandySku,
        candyName: inputCandyName,
        orderQuantity: orderQuantity
      }
      orders.push(order);
    }
    
    this.setState({
      orderQuantities: orders
    });
  };

  setLowInventoryThreshold = (event) => {
    this.setState({lowStockThreshold: event.target.value});
  }

  render() {
    return (
      <>
        <Banner storeName={this.state.storeName}/>

        <div className="lowStockThresholdClass">
          <button type="button" onClick={this.getLowInventory}>Get Low-Stock Items</button>&nbsp;
          <p>Inventory at less than:</p>&nbsp;
          <input id="lowStockThresholdInput" type="number" value={this.state.lowStockThreshold} min="0" max="100" onChange={this.setLowInventoryThreshold} />
          <p>%</p>
        </div>
        
        <table>
          <thead>
            <tr>
              <td>SKU</td>
              <td>Item Name</td>
              <td>Amount in Stock</td>
              <td>Capacity</td>
              <td>Order Amount</td>
            </tr>
          </thead>
          <tbody>    
              {this.state.lowInventory.map(item => 
                (<ItemRow key={item.sku} candy={item} updateOrderQuantity={this.setReorderInventory} />)
              )}
          </tbody>
        </table>
        
        <div className="getReorderCostClass">
          <button type="button" onClick={this.getTotalPrice}>Determine Re-Order Cost</button>&nbsp;
          <div>Total Cost: ${Number(this.state.totalPrice).toFixed(2)}</div>
        </div>
        
        
      </>
  )};
}

export default Challenge; 