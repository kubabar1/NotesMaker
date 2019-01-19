import React, {Component} from 'react';
import {Navbar,Nav,NavItem,NavDropdown,MenuItem,Button} from 'react-bootstrap'
import 'font-awesome/css/font-awesome.min.css';

class Header extends Component {
    render() {
        const isAuthenticated = this.props.isAuthenticated;
        const signout = this.props.signout;
        const handleShowLoginForm = this.props.handleShowLoginForm;

        return (

          <div className="">
            <Navbar style={{marginBottom: "0"}}  collapseOnSelect fluid>
              <Navbar.Header>
                <Navbar.Brand>
                  <i className="fas fa-user"></i><strong>Jan Kowalski</strong>
                </Navbar.Brand>
                <Navbar.Toggle />
              </Navbar.Header>
              <Navbar.Collapse>
                <Nav pullRight>
                  {isAuthenticated ? (
                      <Button bsStyle="info"  onClick={() => signout()}>
                        Log out
                      </Button>
                    ) : (
                    <NavItem>
                      <Button bsStyle="info"  onClick={handleShowLoginForm}>
                        Log in
                      </Button>
                    </NavItem>
                  )}
                </Nav>
              </Navbar.Collapse>
            </Navbar>
          </div>
        );
    }
}

export default Header;
