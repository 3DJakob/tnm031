import React, { useEffect, useState } from 'react'
import styled from 'styled-components'

const Container = styled.div`
    max-width: 800px;
`

const Title = styled.h1`
    color: #fff;
`

const Logs = ({ }) => {
    const [logs, setLogs] = useState([])

    const fetchData = async () => {
        const resp = await window.fetch('http://192.168.1.103:3000/logs')
        const data = await resp.json()
        console.log(data.logs)
        setLogs(data.logs)
    }

    useEffect(() => {
        fetchData()
    }, [])

    return (
        <Container>
            <Title>Secure keylogger</Title>
            <div>
                {logs.map(log => <Log>{log}</Log>)}
            </div>
        </Container>
    )
}

const LogContainer = styled.div`
    background-color: #fff;
    border-radius: 10px;
    padding: 20px;
    margin: 20px;
    box-shadow: 0 0 20px 0 rgba(0, 0, 0, .2);
    white-space: pre;
`

const Log = ({ children }) => {
    return (
        <LogContainer>
            <p>
                {children}
            </p>
        </LogContainer>
    )
}

export default Logs