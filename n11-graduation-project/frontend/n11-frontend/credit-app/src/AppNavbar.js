import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import { Layout, Menu, Breadcrumb } from 'antd';
import './App.css';
import 'antd/dist/antd.css';

const { Header, Content, Footer } = Layout;

export default class AppNavbar extends Component {

    constructor(props) {
        super(props);
        this.state = {isOpen: false};
        this.toggle = this.toggle.bind(this);
        this.defaultSelected = props.defaultSelected;
    }

    toggle() {
        this.setState({
            isOpen: !this.state.isOpen
        });
    }

    render() {
        return (
            <>
                <Layout className="layout" >
                    <Header>
                        <Menu theme="dark" mode="horizontal" defaultSelectedKeys={this.defaultSelected}>
                            <Menu.Item key={1}><Link to="/customers">Customers</Link></Menu.Item>
                            <Menu.Item key={2}><Link to="/credits">Credits</Link></Menu.Item>
                        </Menu>
                    </Header>
                </Layout>
            </>
        )
    }
}