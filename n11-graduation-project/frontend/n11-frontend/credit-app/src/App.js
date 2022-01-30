import React, { Component } from 'react';
import './App.css';
import {BrowserRouter as Router, Redirect, Route, Switch} from 'react-router-dom';
import CustomerList from "./CustomerList";
import CustomerEdit from "./CustomerEdit";
import Credit from "./Credit";


class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={CustomerList}>
                        <Redirect to="/customers" />
                    </Route>
                    <Route path='/customers' exact={true} component={CustomerList}/>
                    <Route path='/customers/:id' component={CustomerEdit}/>
                    <Route path='/credits' exact={true} component={Credit}/>
                </Switch>
            </Router>
        )
    }
}

export default App;