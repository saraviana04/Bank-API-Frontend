import { useState, useEffect } from 'react'
import axios from 'axios'
import UserList from './components/UserList'
import UserForm from './components/UserForm'
import TransferForm from './components/TransferForm'
import { LayoutDashboard } from 'lucide-react'
import './App.css'

function App() {
  const [users, setUsers] = useState([])

  const fetchUsers = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/users')
      setUsers(response.data)
    } catch (error) {
      console.error("Erro ao buscar usuários:", error)
    }
  }

  useEffect(() => {
    fetchUsers()
  }, [])

  return (
    <div className="container">
      <header className="header">
        <div className="logo">
          <LayoutDashboard size={32} />
          <h1>Bank API</h1>
        </div>
        <p>Sistema de Gestão Bancária Premium</p>
      </header>

      <main className="grid-layout">
        <section className="column-left">
          <UserForm onUserAdded={fetchUsers} />
          <TransferForm users={users} onTransferComplete={fetchUsers} />
        </section>

        <section className="column-right">
          <UserList users={users} refreshUsers={fetchUsers} />
        </section>
      </main>
    </div>
  )
}

export default App
