import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { Navbar, NavbarBrand, Nav, NavItem, NavLink, Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import { Link } from 'react-router-dom';
import axios from 'axios';

class AppNavbar extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loggedInUsers: [],
            modalOpen: false
        };
        this.toggleModal = this.toggleModal.bind(this);
        this.handleLogout = this.handleLogout.bind(this);
        this.exportDictionaryToXml = this.exportDictionaryToXml.bind(this); // Bind the new method
    }

    componentDidMount() {
        this.fetchLoggedInUsers();
    }

    fetchLoggedInUsers() {
        fetch('/users')
            .then(response => response.json())
            .then(data => {
                this.setState({ loggedInUsers: data });
            })
            .catch(error => {
                console.error('Error fetching logged-in users:', error);
            });
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

    toggleModal() {
        this.setState(prevState => ({
            modalOpen: !prevState.modalOpen
        }));
    }

    async exportDictionaryToXml() {
        try {
            const response = await axios.get('/export/xml', { responseType: 'blob' });
            const url = window.URL.createObjectURL(new Blob([response.data], { type: 'application/xml' }));
            const a = document.createElement('a');
            a.href = url;
            a.download = 'dictionary.xml';
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
        } catch (error) {
            console.error('Error exporting dictionary:', error);
        }
    }

    render() {
        const { loggedInUsers, modalOpen } = this.state;

        return (
            <Navbar color="dark" dark expand="md">
                <NavbarBrand tag={Link} to="/">Dictionary App</NavbarBrand>
                <Nav className="ml-auto" navbar>
                    <NavItem>
                        <NavLink onClick={this.toggleModal}>Users Info</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink tag={Link} to="/words">Words</NavLink>
                    </NavItem>
                    <NavItem>
                        <Button color="primary" onClick={this.exportDictionaryToXml}>Export to XML</Button>
                    </NavItem>
                    <NavItem>
                        <Button color="danger" onClick={this.handleLogout}>Logout</Button>
                    </NavItem>
                </Nav>
                <Modal isOpen={modalOpen} toggle={this.toggleModal}>
                    <ModalHeader toggle={this.toggleModal}>Logged In Users</ModalHeader>
                    <ModalBody>
                        <ul>
                            {loggedInUsers.map((user, index) => (
                                <li key={index}>{user.username} - {user.loggedIn ? 'Logged In' : 'Logged Out'}</li>
                            ))}
                        </ul>
                    </ModalBody>
                    <ModalFooter>
                        <Button color="secondary" onClick={this.toggleModal}>Close</Button>
                    </ModalFooter>
                </Modal>
            </Navbar>
        );
    }
}

export default withRouter(AppNavbar);
