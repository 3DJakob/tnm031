import React, { useEffect, useState } from 'react'
import styled from 'styled-components'
// max-width: 800px;
const Container = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    flex: 1;
`

const List = styled.div`
    overflow: scroll;
    max-height: 50vh;
`

const Title = styled.h1`
    color: #fff;
`

const ListContainer = styled.div`
    position: relative;
    max-width: 500px;
    width: 100%;
    max-height: 50vh;
`

const Fade = styled.div`
    position: absolute;
    left: 0;
    right: 0;
    bottom: -1px;
    height: 100px;
    background: rgb(229,122,68);
    background: linear-gradient(180deg, rgba(229,122,68,0) 0%, rgba(229,122,68,1) 100%);
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

    if (logs.length == 0) {
        return (<Title>Loading...</Title>)
    }

    return (
        <Container>
            <Title>Secure* keylogger</Title>

            <ListContainer>
                <List>
                    {logs.map(log => <Log>{log}</Log>)}
                </List>
                <Fade></Fade>
            </ListContainer>
        </Container>
    )
}

const LogContainer = styled.div`
    display: flex;
    flex: 1;
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