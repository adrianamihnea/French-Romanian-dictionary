import React, { Component } from 'react';
import { Container, Row, Col, Form, FormGroup, Label, Input, Button } from 'reactstrap';
import { withRouter } from 'react-router-dom';

class Home extends Component {
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
    }
    
     

    handleRegister = async () => {
        // Similar to login, send a POST request to /createUser endpoint with registration data
        // Handle the response accordingly
    }

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
                            {/* Registration form */}
                            {/* Similar to login form, capture input values and handle registration */}
                            {/* You can implement this part similarly to how the login form is implemented */}
                        </Col>
                    </Row>
                </Container>
            </div>
        );
    }
}

export default withRouter(Home);
