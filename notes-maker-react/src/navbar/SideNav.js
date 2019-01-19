import React, {Component} from 'react';
import NavbarLinkContainer from './NavbarLinkContainer'

class SideNav extends Component {
    render() {
        return (
          <div id="sidenav" className="container col-md-2 col-sm-4">
            <NavbarLinkContainer/>
          </div>
        );
    }
}

export default SideNav;
