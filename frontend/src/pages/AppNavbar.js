import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'; // Import withRouter
import { Navbar, NavbarBrand, Nav, NavItem, NavLink, Button } from 'reactstrap';
import { Link } from 'react-router-dom';

class AppNavbar extends Component {
    constructor(props) {
        super(props);
        this.handleLogout = this.handleLogout.bind(this);
    }

    handleLogout() {
        fetch('/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (response.ok) {
                console.log('Logout successful');
                this.props.history.push('/');
            } else {
                console.error('Logout failed');
                // Handle logout failure
            }
        })
        .catch(error => {
            console.error('Error during logout:', error);
        });
    }

    render() {
        return (
            <Navbar color="dark" dark expand="md">
                <NavbarBrand tag={Link} to="/">Dictionary App</NavbarBrand>
                <Nav className="ml-auto" navbar>
                    <NavItem>
                        <NavLink tag={Link} to="/words">Words</NavLink>
                    </NavItem>
                    <NavItem>
                        <Button color="danger" onClick={this.handleLogout}>Logout</Button>
                    </NavItem>
                </Nav>
            </Navbar>
        );
    }
}

export default withRouter(AppNavbar); // Wrap AppNavbar with withRouter
