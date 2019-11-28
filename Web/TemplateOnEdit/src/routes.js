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
import Dashboard from "views/Dashboard.jsx";
import UserProfile from "views/UserProfile.jsx";
//import TableList from "views/TableList.jsx";
//import Typography from "views/Typography.jsx";
//import Icons from "views/Icons.jsx";
//import Maps from "views/Maps.jsx";
//import Notifications from "views/Notifications.jsx";
import JobManagement from "views/JobManagement.jsx";
import EPIManagement from "views/EpiManagement.jsx";
import EmployeeManagement from "views/EmployeeManagement.jsx";
import Upgrade from "views/Upgrade.jsx";
import Arquive from "views/Arquive.jsx";
import Home from "views/Home.jsx";

const dashboardRoutes = [
  
  {
    path: "/home",
    name: "Home",
    icon: "pe-7s-home",
    component: Home,
    layout: "/admin"
  },
  {
    path: "/dashboard",
    name: "Dashboard",
    icon: "pe-7s-graph",
    component: Dashboard,
    layout: "/admin"
  },
  {
    path:"/arquive",
    name:"Arquive",
    icon:"pe-7s-date",
    component: Arquive,
    layout: "/admin"
  },
  {
    path:"/jobs",
    name:"Job Management",
    icon:"pe-7s-portfolio",
    component: JobManagement,
    layout: "/admin"
  },
  {
    path:"/employees",
    name:"Employee Management",
    icon:"pe-7s-id",
    component: EmployeeManagement,
    layout: "/admin"
  },
  {
    path:"/epis",
    name:"EPI Management",
    icon:"pe-7s-global",
    component: EPIManagement,
    layout: "/admin"
  },
  {
    path: "/user",
    name: "User Profile",
    icon: "pe-7s-user",
    component: UserProfile,
    layout: "/admin",
    invisible:true
  },
  /*{
    path:"/",
    name:"Arquive",
    icon:"pe-7s-date",
    component: Arquive,
    layout: "/admin"
  },
  
  ,
  {
    path: "/table",
    name: "Table List",
    icon: "pe-7s-note2",
    component: TableList,
    layout: "/admin"
  },
  {
    path: "/typography",
    name: "Typography",
    icon: "pe-7s-news-paper",
    component: Typography,
    layout: "/admin"
  },
  {
    path: "/icons",
    name: "Icons",
    icon: "pe-7s-science",
    component: Icons,
    layout: "/admin"
  },
  {
    path: "/maps",
    name: "Maps",
    icon: "pe-7s-map-marker",
    component: Maps,
    layout: "/admin"
  },
  {
    path: "/notifications",
    name: "Notifications",
    icon: "pe-7s-bell",
    component: Notifications,
    layout: "/admin"
  },*/
  {
    path: "",
    name: "Home",
    icon: "pe-7s-home",
    component: Upgrade,
    layout: "/admin",
    invisible:true,
    
  }
  
];

export default dashboardRoutes;
