import React, {Component} from 'react';
import AppNavbar from './AppNavbar';
import './App.css';
import 'antd/dist/antd.css';
import {Tabs, Card, Row, Form, Input, Button, DatePicker,} from 'antd';
import {Footer} from "antd/es/layout/layout";
import { notification } from 'antd';
import { CheckCircleFilled, CloseCircleFilled } from '@ant-design/icons';

const {TabPane} = Tabs;

class Credit extends Component {

    constructor(props) {
        super(props);
    }

    async postApply(data) {

        await fetch(`/api/v1/credits?userIdNumber=${data.id}`, {
            headers: {
                Accept: 'application/json',
            },
            method: 'POST'
        }).then(response => {
            if (response.ok) {
                response.json().then(json => {
                    notification.open({
                        message: 'Credit Application Accepted',
                        description:
                            `ID number: ${json.customerId}'s credit application confirmed.\n Credit limit: ${json.creditLimit}`,
                        icon: <CheckCircleFilled style={{ color: '#108ee9' }} />,
                        onClick: () => {
                            console.log('Notification Clicked!');
                        },
                    });
                    return response;
                });
            } else {
                notification.open({
                    message: 'Credit Application Result',
                    description:
                        `Credit application is not confirmed.\n Credit limit: 0`,
                    icon: <CloseCircleFilled style={{ color: '#108ee9' }} />,
                    onClick: () => {
                        console.log('Notification Clicked!');
                    },
                });
            }
        });
    }

    async getResult(data) {
        // debugger;
        let response = await fetch(`/api/v1/credits?userIdNumber=${data.id}&dateOfBirth=${data.date['_i']}`, {
            headers: {
                Accept: 'application/json',
            },
            method: 'GET'
        }).then(response => {
            if (response.ok) {
                response.json().then(json => {
                    notification.open({
                        message: 'Credit Application Result',
                        description:
                            `ID number: ${json.customerId}'s credit application result.\n Credit limit: ${json.creditLimit}`,
                        icon: <CheckCircleFilled style={{ color: '#108ee9' }} />,
                        onClick: () => {
                            console.log('Notification Clicked!');
                        },
                    });
                    return response;
                });
            } else {
                notification.open({
                    message: 'Credit Application Result',
                    description:
                        `Credit application result.\n Credit limit: 0`,
                    icon: <CloseCircleFilled style={{ color: '#108ee9' }} />,
                    onClick: () => {
                        console.log('Notification Clicked!');
                    },
                });
            }
        });
    }

    render() {

        const applyCredit = () => {
            return <Card class="second" title="APPLY CREDIT" bordered={false}
                         style={{minHeight: 270, borderRadius: 30, width: 400}}>
                <Form onFinish={this.postApply}>
                    <Form.Item
                        name='id'
                        label="T.R. ID Number"
                        rules={[
                            {
                                required: true,
                            },
                        ]}
                    >
                        <Input className="text-field"/>
                    </Form.Item>
                    <br></br>
                    <br></br>

                    <Form.Item>
                        <Button className="button" type="primary" htmlType="submit">
                            APPLY
                        </Button>
                    </Form.Item>
                </Form>
            </Card>
        }

        const creditResult = () => {
            return <Card class="second" title="CREDIT RESULT" bordered={false}
                         style={{minHeight: 240, borderRadius: 30, width: 400}}>
                <Form onFinish={this.getResult}>
                    <Form.Item
                        name='id'
                        label="T.R. ID Number"
                        rules={[
                            {
                                required: true,
                            },
                        ]}
                    >
                        <Input className="text-field"/>
                    </Form.Item>

                    <Form.Item label="DatePicker" name='date'
                               rules={[
                                   {
                                       required: true,
                                   },
                               ]}>
                        <DatePicker style={{ marginLeft: 25, width: 235}}/>
                    </Form.Item>

                    <Form.Item>
                        <Button className="button" type="primary" htmlType="submit">
                            SEARCH
                        </Button>
                    </Form.Item>
                </Form>
            </Card>
        }

        return (
            <div className="App">
                <AppNavbar defaultSelected ="2"/>
                <Row type="flex" justify="center" align="middle" style={{minHeight: '100vh'}}>
                    <Tabs defaultActiveKey="1" centered style={{backgroundColor:'#ffffff', borderRadius:50}}>
                        <TabPane tab="Apply Credit" key="1">
                            {applyCredit()}
                        </TabPane>
                        <TabPane tab="Credit Result" key="2">
                            {creditResult()}
                        </TabPane>
                    </Tabs>
                </Row>
                <Footer style={{ textAlign: 'center' }}>Serkan Bodur Â©2022 Created for N-11</Footer>
            </div>
        );
    }
}

export default Credit;