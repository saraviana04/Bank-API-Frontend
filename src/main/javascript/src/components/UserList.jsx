import React, { useEffect } from 'react';
import { Users, CreditCard } from 'lucide-react';

export default function UserList({ users, refreshUsers }) {
  
  useEffect(() => {
    refreshUsers();
  }, []);

  return (
    <div className="card">
      <div className="card-header">
        <Users className="icon" />
        <h2>Usuários ({users.length})</h2>
      </div>
      <div className="user-list">
        {users.length === 0 ? (
          <p className="empty-state">Nenhum usuário cadastrado.</p>
        ) : (
          users.map((user) => (
            <div key={user.id} className="user-item">
              <div className="user-info">
                <h3>{user.nome}</h3>
                <span className="user-meta">Idade: {user.idade} | CPF: {user.cpf}</span>
              </div>
              <div className="user-account">
                <span className="account-number">Conta: {user.numeroConta}</span>
                <span className="account-balance">
                  {user.saldo.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}
                </span>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
}
