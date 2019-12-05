import decode from 'jwt-decode';
require('es6-promise').polyfill();
require('isomorphic-fetch');
export default class AuthService {
    // Initializing important variables
    constructor(domain) {
        this.domain = domain || 'https://25.63.33.7:5001' // API server domain
        this.fetch = this.fetch.bind(this) // React binding stuff
        this.login = this.login.bind(this)
        this.getProfile = this.getProfile.bind(this)
    }

    login(username, password) {
        // Get a token from api server using the fetch api
        
        return this.fetch("https://192.168.1.66:5001/users/authenticate", {
            method: 'POST',
            body: JSON.stringify({
                "username":username,
                "password":password
            })
        }).then(res => {
            this.setUser(res.id,res.username)
            this.setToken(res.token) // Setting the token in localStorage
            return Promise.resolve(res);
        }).catch(error => alert('Error! ' + error.message))
    }

    loggedIn() {
        // Checks if there is a saved token and it's still valid
        const token = this.getToken() // GEtting token from localstorage
        return !!token && !this.isTokenExpired(token) // handwaiving here
    }

    isTokenExpired(token) {
        try {
            const decoded = decode(token);
            if (decoded.exp < Date.now() / 1000) { // Checking if token is expired. N
                return true;
            }
            else
                return false;
        }
        catch (err) {
            return false;
        }
    }

    setToken(idToken) {
        // Saves user token to localStorage
        localStorage.setItem('id_token', idToken)
    }
    setUser(idUser,user){
        localStorage.setItem("idUser",idUser)
        localStorage.setItem("User",user)
        
    }

    getToken() {
        // Retrieves the user token from localStorage
        return localStorage.getItem('id_token')
    }

    logout() {
        // Clear user token and profile data from localStorage
        localStorage.removeItem('id_token');
    }

    getProfile() {
        // Using jwt-decode npm package to decode the token
        return decode(this.getToken());
    }
    getRole(){

        return decode(this.getToken())["role"];
    }
    roleInUsers(users){
         var ola=false;
        for(var i=0;i<users.length;i++){
            if(users[i]===this.getRole())ola=true;
        }
        return ola;
    }
    fetch(url, options) {
        // performs api calls sending the required authentication headers
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }

        // Setting Authorization header
        // Authorization: Bearer xxxxxxx.xxxxxxxx.xxxxxx
        if (this.loggedIn()) {
            headers['Authorization'] = 'Bearer ' + this.getToken()
        }

        return fetch(url, {
            headers,
            ...options
        })
            .then(this._checkStatus)
            .then(response => response.json())
    }

    _checkStatus(response) {
        // raises an error in case response status is not a success
        if (response.status >= 200 && response.status < 300) { // Success status lies between 200 to 300
            return response
        } else {
            var error = new Error(response.statusText)
            error.response = response
            throw error
        }
    }
}