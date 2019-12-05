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
import { epis } from "variables/Variables.jsx";

import Card from "components/Card/Card.jsx";
class TableList extends Component {
  thArray=["Nome","ID","Data Registo","Data Validade","Inspetor","Detalhes"];
  render() {
    return (
      <div className="content">
      <Grid fluid>
        <Row>
          <Col md={12}>
            <Card
              title="EPI Management"
              ctTableFullWidth
              ctTableResponsive
              content={
                <div className="tabela">
                <Table striped hover>
                  <thead>
                    <tr>
                      {this.thArray.map((prop, key) => {
                        return <th key={key}>{prop}</th>;
                      })}
                    </tr>
                  </thead>
                  <tbody>
                    {epis.map((prop, key) => {
                      return (
                        <tr key={key}>
                    <td>{prop[0]}</td>
                    <td>{prop[1]}</td>
                    <td>{prop[2]}</td>
                    <td>{prop[3]}</td>
                    <td>{prop[4]}</td>
                    <td><a href={"/epis/"+prop[1]}>Detalhes</a></td>
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
