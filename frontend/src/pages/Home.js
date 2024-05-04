import React, { Component } from 'react';
import { Container, Row, Col, Form, FormGroup, Label, Input, Button } from 'reactstrap';
import { withRouter } from 'react-router-dom';

class Home extends Component {
    state = {
        showRegistrationForm: false,
        registrationData: {
            username: '',
            password: ''
        }
    };

    handleLogin = async () => {
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        try {
            const response = await fetch(`/login?username=${username}&password=${password}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (response.ok) {
                this.props.history.push('/words');
            } else {
                const errorMessage = await response.text(); // Get error message from response
                if (errorMessage.includes('User not registered')) {
                    alert('User not registered'); // Display error message for unregistered user
                } else if (errorMessage.includes('Invalid credentials')) {
                    alert('Invalid credentials'); // Display error message for incorrect credentials
                } else {
                    alert('Unknown error'); // Display generic error message for other errors
                }
            }
        } catch (error) {
            console.error('Error logging in:', error);
            alert('Error logging in'); // Display generic error message to user
        }
    };

    handleRegister = async () => {
        const { username, password } = this.state.registrationData;
        try {
            const response = await fetch('/createUser', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username, password })
            });
    
            if (response.ok) {
                alert('Registration successful');
                // Optionally, you can automatically log in the user after registration
                // Redirect the user to the login page or automatically log them in
            } else {
                const errorMessage = await response.text(); // Get error message from response
                alert(errorMessage); // Display error message
            }
        } catch (error) {
            console.error('Error registering user:', error);
            alert('Error registering user'); // Display generic error message to user
        }
    };
    

    toggleRegistrationForm = () => {
        this.setState(prevState => ({
            showRegistrationForm: !prevState.showRegistrationForm
        }));
    };

    handleInputChange = e => {
        const { name, value } = e.target;
        this.setState(prevState => ({
            registrationData: {
                ...prevState.registrationData,
                [name]: value
            }
        }));
    };

    render() {
        return (
            <div>
                <Container>
                    <Row>
                        <Col>
                            <h1>Welcome to Our Dictionary</h1>
                            <p>
                                Our dictionary provides comprehensive translations between French and Romanian words.
                                Whether you're learning a new language or need to translate text, our dictionary is
                                here to help you.
                            </p>
                        </Col>
                    </Row>
                    <Row>
                        <Col md={{ size: 6, offset: 3 }}>
                            <h3>Login</h3>
                            <Form>
                                <FormGroup>
                                    <Label for="username">Username</Label>
                                    <Input type="text" name="username" id="username" placeholder="Enter your username" />
                                </FormGroup>
                                <FormGroup>
                                    <Label for="password">Password</Label>
                                    <Input type="password" name="password" id="password" placeholder="Enter your password" />
                                </FormGroup>
                                <Button color="primary" onClick={this.handleLogin}>Login</Button>
                            </Form>
                        </Col>
                    </Row>
                    <Row>
                        <Col md={{ size: 6, offset: 3 }}>
                            <h3>Register</h3>
                            <Button color="info" onClick={this.toggleRegistrationForm}>Register</Button>
                            {/* Conditionally render the registration form based on state */}
                            {this.state.showRegistrationForm && (
                                <Form>
                                    <FormGroup>
                                        <Label for="regUsername">Username</Label>
                                        <Input type="text" name="username" id="regUsername" placeholder="Enter your username" onChange={this.handleInputChange} />
                                    </FormGroup>
                                    <FormGroup>
                                        <Label for="regPassword">Password</Label>
                                        <Input type="password" name="password" id="regPassword" placeholder="Enter your password" onChange={this.handleInputChange} />
                                    </FormGroup>
                                    <Button color="success" onClick={this.handleRegister}>Register</Button>
                                </Form>
                            )}
                        </Col>
                    </Row>
                </Container>
            </div>
        );
    }
}

export default withRouter(Home);
