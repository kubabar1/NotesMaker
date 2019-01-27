import React, { Component } from 'react';
import Header from "./header/Header";
import SideNav from "./navbar/SideNav";
import MyNotes from "./main/noteslist/MyNotes";
import PublicNotes from "./main/noteslist/PublicNotes";
import AddNote from "./main/addnote/AddNote";
import NoteView from "./main/noteview/NoteView";
import Settings from "./main/settings/Settings";
import ChangePassword from "./main/settings/ChangePassword";
import Login from "./login/Login";
import {Redirect, Switch, Route} from "react-router-dom";
import {LOGIN_ENDPOINT,LOGOUT_ENDPOINT,CHECK_AUTHENTICATED,CURRENT_USER} from "./environment";
import { withCookies } from 'react-cookie';

class App extends Component {

  constructor(props, context) {
    super(props, context);
    const {cookies} = props;



    this.state = {
      showLoginForm: false,
      isAuthenticated: false,
      componentLoaded:false,
      currentUser:null,
      csrfToken:cookies.get('XSRF-TOKEN')
    };
  }


  componentDidMount(){

    fetch(CHECK_AUTHENTICATED, {
        method: 'GET',
        headers: {
            'X-XSRF-TOKEN': this.state.csrfToken
        },
        credentials: 'include'
    })
    .then(response => {
      if(response.ok){
        this.getCurrentUser();
        this.setState({ isAuthenticated:true, componentLoaded:true });
      }else{
        this.setState({ isAuthenticated:false, componentLoaded:true });
      }
    })
    .catch(e => console.log(e));
  }

  getCurrentUser = () => {
    fetch(CURRENT_USER,{
      headers: {
          'X-XSRF-TOKEN': this.state.csrfToken
      },
      credentials: "include"})
    .then(resp => resp.json())
    .then(json=>this.setState({
      currentUser:json
    }))
    .catch(e => console.log(e))
  }

  authenticate = (cb, username, password) => {

    const formData = new FormData();

    formData.append("username", username);
    formData.append("password", password);

    console.log(this.state.csrfToken)

    fetch(LOGIN_ENDPOINT, {
        method: "POST",
          headers: {
            'X-XSRF-TOKEN': this.state.csrfToken
          },
          body: formData,
          credentials: 'include'
      }).then(response => {
        if(response.ok){
          this.getCurrentUser();
          this.setState({ isAuthenticated:true });
        }else{
          this.setState({ isAuthenticated:false });
        }
      }).catch(e => console.log(e));
    setTimeout(cb, 1000);
  }

  signout = (cb) => {
    fetch(LOGOUT_ENDPOINT, {
      method: 'POST',
      headers: {
          'X-XSRF-TOKEN': this.state.csrfToken
      },
      credentials: 'include',
    })
    .then(e => this.setState({ isAuthenticated:false }))
    .catch(e => console.log(e));

    return(<Redirect to={{ pathname: "/" }}/>);
  }

  handleCloseLoginForm = () => {
    this.setState({ showLoginForm: false });
  }

  handleShowLoginForm  = () => {
    this.setState({ showLoginForm: true });
  }

  render() {

    return (
      <div className="container-fluid">
        <div className="row">
          <Header
              handleShowLoginForm={this.handleShowLoginForm}
              isAuthenticated={this.state.isAuthenticated}
              authenticate={this.authenticate}
              signout={this.signout}
              currentUser={this.state.currentUser}/>
          <SideNav/>
        	<div id="main_content" className="container-fluid col-md-10 col-sm-8">
            {this.state.componentLoaded?(
              <Switch>
                <Route exact path="/" component={Empty}/>
                <Route path="/login" component={Empty}/>
                <PrivateRoute
                  path="/my-notes"
                  component={MyNotes}
                  isAuthenticated={this.state.isAuthenticated}
                  handleShowLoginForm={this.handleShowLoginForm}
                  componentLoaded={this.state.componentLoaded}/>
                <PrivateRoute
                  path="/public-notes"
                  component={PublicNotes}
                  isAuthenticated={this.state.isAuthenticated}
                  handleShowLoginForm={this.handleShowLoginForm}
                  componentLoaded={this.state.componentLoaded}/>
                <PrivateRoute
                  path="/add-note"
                  component={AddNote}
                  isAuthenticated={this.state.isAuthenticated}
                  handleShowLoginForm={this.handleShowLoginForm}
                  componentLoaded={this.state.componentLoaded}/>
                <PrivateRoute
                  path="/settings"
                  component={(props) => <Settings {...props}
                                          currentUser={this.state.currentUser}
                                          getCurrentUser={this.getCurrentUser}/>}
                  isAuthenticated={this.state.isAuthenticated}
                  handleShowLoginForm={this.handleShowLoginForm}
                  componentLoaded={this.state.componentLoaded}/>
                <PrivateRoute
                  path="/change-password"
                  component={ChangePassword}
                  isAuthenticated={this.state.isAuthenticated}
                  handleShowLoginForm={this.handleShowLoginForm}
                  componentLoaded={this.state.componentLoaded}/>
                <PrivateRoute
                  path="/:author/notes/:id"
                  component={(props) => <NoteView {...props}
                                          currentUser={this.state.currentUser}/>}
                  isAuthenticated={this.state.isAuthenticated}
                  handleShowLoginForm={this.handleShowLoginForm}
                  componentLoaded={this.state.componentLoaded}
                  />
              </Switch>):""
            }
          </div>
        </div>

        <Login
          show={this.state.showLoginForm}
          handleShow={this.handleShowLoginForm}
          handleClose={this.handleCloseLoginForm}
          isAuthenticated={this.state.isAuthenticated}
          authenticate={this.authenticate}
          signout={this.signout}/>
      </div>
    );
  }
}

export default withCookies(App);

const Empty = () => {
  return "";
}

const PrivateRoute = ({ component: Component, isAuthenticated, handleShowLoginForm, componentLoaded, ...rest }) => {

  if(!isAuthenticated && componentLoaded){
    handleShowLoginForm();
  }

  return (
    <Route
      {...rest}
      render={props =>
        isAuthenticated ? (
          <Component {...props} />
        ) : (
          <Redirect
            to={{
              pathname: "/login",
              state: { from: props.location }
            }}
          />
        )
      }
    />
  );
}
