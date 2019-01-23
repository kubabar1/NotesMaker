import React, {Component} from 'react';
import {Navbar,Nav,NavItem,Button} from 'react-bootstrap'
import 'font-awesome/css/font-awesome.min.css';
import {CURRENT_USER} from "../environment";

class Header extends Component {

    render() {
        const isAuthenticated = this.props.isAuthenticated;
        const signout = this.props.signout;
        const handleShowLoginForm = this.props.handleShowLoginForm;

        const currentUser = this.props.currentUser;

        return (
          <div>
            <Navbar style={{marginBottom: 0}}  collapseOnSelect inverse staticTop fluid>
              <Navbar.Header>
                <Navbar.Brand>
                  {currentUser&&isAuthenticated  ? <div><i className="fas fa-user"></i><strong>{currentUser.name+" "+currentUser.surname}</strong></div> : ""}
                </Navbar.Brand>
              </Navbar.Header>
              <Navbar.Link pullRight>
                  {isAuthenticated ? (
                      <Button bsStyle="info" className="m-3" onClick={() => signout()}>
                        Log out
                      </Button>
                    ) : (
                      <Button bsStyle="info" className="m-3" onClick={handleShowLoginForm}>
                        Log in
                      </Button>
                  )}
              </Navbar.Link>
            </Navbar>
          </div>
        );
    }
}

export default Header;
