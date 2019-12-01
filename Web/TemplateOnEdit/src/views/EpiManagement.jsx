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
import {Card,CardColumns,CardBody,CardTitle,CardText,CardLink} from "reactstrap";
import {Row, Col } from "react-bootstrap";
import { jobs } from "variables/Variables.jsx";

class TableList extends Component {
  render() {
    return (
      <div className="content">
        <Row>
        <Col lg={10}><h2>EPI Management</h2></Col><Col lg={2}><a href="#"><h6>Adicionar<i className="fa fa-plus-circle fa-3x" /></h6></a></Col>
        </Row>
        <CardColumns>
        {
          jobs.map((prop,key)=>{
            
            return(
            <Card>
              <CardBody>
                <CardTitle>{prop[0]}</CardTitle>
                <CardText>{prop[1].map((props)=>{
                  return props+"\n"
                })}</CardText>
                <CardLink href="#">Edit</CardLink>
                <CardLink href="#" >Delete</CardLink>
                </CardBody>
              </Card>
            )
          })
        }
        </CardColumns>
        
      </div>
    );
  }
}

export default TableList;
