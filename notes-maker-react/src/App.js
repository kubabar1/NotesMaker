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

class App extends Component {

  constructor(props, context) {
    super(props, context);

    this.state = {
      showLoginForm: false,
      isAuthenticated: true,
      componentLoaded:false
    };
  }


  componentDidMount(){
    /*fetch(CHECK_AUTHENTICATED, {
        credentials: 'include',
        method: 'GET'
    })
    .then(response => {
      response.ok ? this.setState({ isAuthenticated:true, componentLoaded:true }) : this.setState({ isAuthenticated:false, componentLoaded:true });
      console.log(response);
    }).catch(e => console.log(e));*/
    this.setState({ componentLoaded:true })
  }

  authenticate = (cb, username, password) => {

    /*const formData = new FormData();

    formData.append("username", username);
    formData.append("password", password);

    fetch(LOGIN_ENDPOINT, {
        credentials: 'include',
        method: 'POST',
        body: formData
      }).then(response => {
        response.status===200 ? this.setState({ isAuthenticated:true }) : this.setState({ isAuthenticated:false });
        console.log(response);
      }).catch(e => console.log(e));*/
    setTimeout(cb, 700);
  }

  signout = (cb) => {
    /*fetch(LOGOUT_ENDPOINT, {
      credentials: 'include',
      method: 'POST',
    })
    .then(e => this.setState({ isAuthenticated:false }))
    .catch(e => console.log(e));*/

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
              signout={this.signout}/>
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
                  path="/notes/:id"
                  component={NoteView}
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
                  component={Settings}
                  isAuthenticated={this.state.isAuthenticated}
                  handleShowLoginForm={this.handleShowLoginForm}
                  componentLoaded={this.state.componentLoaded}/>
                <PrivateRoute
                  path="/change-password"
                  component={ChangePassword}
                  isAuthenticated={this.state.isAuthenticated}
                  handleShowLoginForm={this.handleShowLoginForm}
                  componentLoaded={this.state.componentLoaded}/>
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

export default App;

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
