import React, { Component } from 'react';
import './App.css';
import Home from './pages/Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import WordList from './pages/WordList';
import WordEdit from './pages/WordEdit';
import TranslationList from './pages/TranslationList';
import AddTranslation from './pages/AddTranslation';
import EditTranslation from './pages/EditTranslation'; // Make sure to import EditTranslation component

class App extends Component {
  render() {
    return (
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home}/>
            <Route path='/words' exact={true} component={WordList}/>
            <Route path="/words/:id/translations/new" component={AddTranslation} /> 
            <Route path="/words/:id/translations/:translationId/edit" component={EditTranslation} /> 
            <Route path="/words/:id/translations" component={TranslationList} />
            <Route path='/words/:id' component={WordEdit}/>
          </Switch>
        </Router>
    )
  }
}

export default App;
