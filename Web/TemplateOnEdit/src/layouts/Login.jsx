import React, { Component } from 'react';
import 'assets/css/login.css';
import AuthService from 'components/Authentication/AuthService';
class Login extends Component {
    constructor(){
        super();
        this.handleChange = this.handleChange.bind(this);
        this.handleFormSubmit = this.handleFormSubmit.bind(this);
        this.Auth = new AuthService();
    }
    handleChange(e){
        this.setState(
            {
                
                [e.target.name]: e.target.value
            }
        )
    }
    handleFormSubmit(e){
        e.preventDefault();
      
        this.Auth.login(this.state.username,this.state.password)
            .then(res =>{
                
               this.props.history.replace('/');
            })
    }
    componentWillMount(){
        if(this.Auth.loggedIn())
            this.props.history.replace('/');
    }
    render() {
        return (
            <div className="center">
                <div className="cardes">
                    <h2 >EVITAR</h2>
                    <p>Welcome back! Please login to your account.</p>
                    <form>
                        <input
                            className="form-item"
                            placeholder="Username goes here..."
                            name="username"
                            type="text"
                            onChange={this.handleChange}
                        />
                        <input
                            className="form-item"
                            placeholder="Password goes here..."
                            name="password"
                            type="password"
                            onChange={this.handleChange}
                        />
                        <input
                            className="form-submit"
                            value="SUBMIT"
                            type="submit"
                            onClick={this.handleFormSubmit}
                        />
                    </form>
                </div>
            </div>
        );
    }

   
}

export default Login;