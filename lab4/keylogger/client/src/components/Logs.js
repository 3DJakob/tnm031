import React, { useEffect, useState } from 'react'
import styled from 'styled-components'
import FuzzySearch from 'fuzzy-search'
import moment from 'moment'

// max-width: 800px;
const Container = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    flex: 1;
    max-width: 700px;
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

const SearchBox = styled.input`
    display: flex;
    padding: 20px;
    margin: 20px;
    font-size: 16px;
    flex: 1;
    width: -webkit-fill-available;
    background-color: #fff;
    border-radius: 10px;
    -webkit-appearance: none;
    border:none;
    background-image:none;
    -webkit-box-shadow: none;
    -moz-box-shadow: none;
    box-shadow: none;
    box-sizing: border-box;
`

const Logs = ({ }) => {
    const [logs, setLogs] = useState([])
    const [search, setSearch] = useState('')

    const searcher = new FuzzySearch(logs, ['text'], {
        caseSensitive: false,
      });

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

    const result = searcher.search(search)

    return (
        <Container>
            <Title>Secure* keylogger</Title>

            <ListContainer>
                <List>
                    {result.map(log => <Log data={log}>{log.text.replaceAll('cmd', '⌘').replaceAll('Shift', '⇧').replaceAll('Enter', '⏎')}</Log>)}
                </List>
                <Fade></Fade>
            </ListContainer>
            <SearchBox onChange={(e) => setSearch(e.target.value)} placeholder='Search...' ></SearchBox>
        </Container>
    )
}

const LogContainer = styled.div`
    display: flex;
    flex: 1;
    background-color: #fff;
    border-radius: 10px;
    padding: 26px;
    margin: 20px;
    box-shadow: 0 0 20px 0 rgba(0, 0, 0, .2);
    flex-direction: column;
`

const Text = styled.p`
    margin: 0;
    hyphens: auto;
    width: 100%;
    white-space: pre-wrap;      /* CSS3 */   
    white-space: -moz-pre-wrap; /* Firefox */    
    white-space: -pre-wrap;     /* Opera <7 */   
    white-space: -o-pre-wrap;   /* Opera 7 */    
    word-wrap: break-word;      /* IE */
`

const Timestamp = styled.p`
    margin: 0;
    color: #ccc;
    white-space: pre;
    text-align: right;
`

const Log = ({ children, data }) => {
    return (
        <LogContainer>
            <Text>
                {children}
            </Text>
            <Timestamp>
                {moment(data.timestamp).fromNow()}
            </Timestamp>
        </LogContainer>
    )
}

export default Logs