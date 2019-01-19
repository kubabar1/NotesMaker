import React, {Component} from 'react';
import {Navbar,Nav,NavItem,Button} from 'react-bootstrap'
import 'font-awesome/css/font-awesome.min.css';

class Header extends Component {
    render() {
        const isAuthenticated = this.props.isAuthenticated;
        const signout = this.props.signout;
        const handleShowLoginForm = this.props.handleShowLoginForm;

        return (
          <div>
            <Navbar style={{marginBottom: 0}}  collapseOnSelect inverse staticTop fluid>
              <Navbar.Header>
                <Navbar.Brand>
                  <i className="fas fa-user"></i><strong>Jan Kowalski</strong>
                </Navbar.Brand>
              </Navbar.Header>
              <Navbar.Link pullRight>
                  {isAuthenticated ? (
                      <Button bsStyle="info" className="m-3" onClick={() => signout()}>
                        Log out
                      </Button>
                    ) : (
                    <NavItem>
                      <Button bsStyle="info" className="m-3" onClick={handleShowLoginForm}>
                        Log in
                      </Button>
                    </NavItem>
                  )}
              </Navbar.Link>
            </Navbar>
          </div>
        );
    }
}

export default Header;
