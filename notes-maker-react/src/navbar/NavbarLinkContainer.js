import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import {Collapse} from 'react-bootstrap';

class NavbarLinkContainer extends Component {
    constructor(props, context) {
      super(props, context);

      this.state = {
        open: false
      };
    }

    render() {
        return (
          <div id="sidenav-content" className="col-2 p-0 m-0">
            <ul className="list-group">
              <Link to={'/my-notes'} className="">
                <li className="bg-dark nav-link">
                  <div>
                    <i className="far fa-sticky-note"></i>
                    <span>Notes</span>
                  </div>
                </li>
              </Link>
              <Link to={'/add-note'} className="">
                <li className="bg-dark nav-link">
                  <div>
                    <i className="fas fa-plus"></i>
                    <span>Add note</span>
                  </div>
                </li>
              </Link>
              <Link to={'/settings'} className="">
                <li className="bg-dark nav-link">
                  <div>
                    <i className="fas fa-cog"></i>
                    <span>Settings</span>
                  </div>
                </li>
              </Link>
            </ul>
          </div>
        );
    }
}

export default NavbarLinkContainer;
