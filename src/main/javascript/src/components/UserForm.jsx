import React, { useState } from 'react';
import { UserPlus, Save } from 'lucide-react';
import axios from 'axios';

export default function UserForm({ onUserAdded }) {
    const [formData, setFormData] = useState({
        nome: '',
        idade: '',
        cpf: '',
        saldo: 0
    });
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setMessage(null);

        try {
            const response = await axios.post('http://localhost:8080/api/users', formData);
            setMessage({ type: 'success', text: 'Usuário cadastrado com sucesso!' });
            setFormData({ nome: '', idade: '', cpf: '', saldo: 0 });
            if (onUserAdded) onUserAdded();
        } catch (error) {
            console.error("Erro no cadastro:", error);
            let errorMsg = 'Erro ao cadastrar usuário';

            if (error.response) {
                // O servidor respondeu com um status de erro
                errorMsg = error.response.data?.message || `Erro do servidor (${error.response.status})`;
            } else if (error.request) {
                // A requisição foi feita mas sem resposta (erro de rede/CORS)
                errorMsg = 'Erro de conexão com o servidor using.';
            } else {
                errorMsg = error.message;
            }

            setMessage({ type: 'error', text: errorMsg });
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="card">
            <div className="card-header">
                <UserPlus className="icon" />
                <h2>Novo Cadastro</h2>
            </div>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Nome Completo</label>
                    <input
                        type="text"
                        required
                        value={formData.nome}
                        onChange={(e) => setFormData({ ...formData, nome: e.target.value })}
                        placeholder="Ex: João da Silva"
                    />
                </div>
                <div className="form-row">
                    <div className="form-group">
                        <label>Idade</label>
                        <input
                            type="number"
                            required
                            min="18"
                            value={formData.idade}
                            onChange={(e) => setFormData({ ...formData, idade: e.target.value })}
                        />
                    </div>
                    <div className="form-group">
                        <label>Saldo Inicial</label>
                        <input
                            type="number"
                            required
                            min="0"
                            step="0.01"
                            value={formData.saldo}
                            onChange={(e) => setFormData({ ...formData, saldo: e.target.value })}
                        />
                    </div>
                </div>
                <div className="form-group">
                    <label>CPF</label>
                    <input
                        type="text"
                        required
                        maxLength="11"
                        value={formData.cpf}
                        onChange={(e) => setFormData({ ...formData, cpf: e.target.value })}
                        placeholder="Apenas números"
                    />
                </div>

                {message && (
                    <div className={`message ${message.type}`}>
                        {message.text}
                    </div>
                )}

                <button type="submit" className="btn-primary" disabled={loading}>
                    {loading ? 'Salvando...' : (
                        <>
                            <Save size={18} /> Cadastrar
                        </>
                    )}
                </button>
            </form>
        </div>
    );
}
