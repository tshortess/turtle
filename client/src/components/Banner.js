import React, { Component } from "react";

class Banner extends Component {
    
    render() {
      return (
        <>
          <h1 className="bannerHeaderClass">{this.props.storeName} Inventory </h1>
        </>
      );
    }
}

export default Banner;