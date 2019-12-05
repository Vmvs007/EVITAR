/*!

=========================================================
* Light Bootstrap Dashboard React - v1.3.0
=========================================================

* Product Page: https://www.creative-tim.com/product/light-bootstrap-dashboard-react
* Copyright 2019 Creative Tim (https://www.creative-tim.com)
* Licensed under MIT (https://github.com/creativetimofficial/light-bootstrap-dashboard-react/blob/master/LICENSE.md)

* Coded by Creative Tim

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/
import React, { Component } from "react";
import { Grid, Row, Col, Table } from "react-bootstrap";

import Card from "components/Card/Card.jsx";
import { thArray, tdArray } from "variables/Variables.jsx";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
class TableList extends Component {
  state = {
    ola:"coisaBoa",
    startDate: new Date()
  };
 
  handleChange = date => {
    this.setState({
      startDate: date,
      ola:date.toString()
    });
  };
  render() {
    return (
      <div className="content">
        <Grid fluid>
          <Row>
            <Col md={12}>
              <Card
                title="Archive"
                content={
                  <DatePicker
                  dateFormat="yyyy/MM/dd"
                  selected={this.state.startDate}
                  onChange={this.handleChange}
                />
                }/> 
            </Col>
          </Row>
          <Row>
            <Col md={12}>
              <Card
                title="Archive"
                category="Here is a subtitle for this table"
                ctTableFullWidth
                ctTableResponsive
                content={
                  <div className="tabela">
                    
                  <Table striped hover>
                    <thead>
                      <tr>
                        {thArray.map((prop, key) => {
                          return <th key={key}>{prop}</th>;
                        })}
                      </tr>
                    </thead>
                    <tbody>
                      {tdArray.map((prop, key) => {
                        return (
                          <tr key={key}>
                            {prop.map((prop, key) => {
                              return <td key={key}>{prop}</td>;
                            })}
                          </tr>
                        );
                      })}
                    </tbody>
                  </Table>
                  </div>
                }
              />
            </Col>

            
          </Row>
        </Grid>
      </div>
    );
  }
}

export default TableList;
