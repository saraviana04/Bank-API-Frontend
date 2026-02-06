import React, { useState } from 'react';
import { ArrowRightLeft, Send } from 'lucide-react';
import axios from 'axios';

export default function TransferForm({ users, onTransferComplete }) {
    const [formData, setFormData] = useState({
        contaOrigem: '',
        contaDestino: '',
        valor: ''
    });
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!formData.contaOrigem || !formData.contaDestino) {
            setMessage({ type: 'error', text: 'Selecione as contas de origem e destino.' });
            return;
        }
        if (formData.contaOrigem === formData.contaDestino) {
            setMessage({ type: 'error', text: 'A conta de origem e destino não podem ser iguais.' });
            return;
        }

        setLoading(true);
        setMessage(null);

        try {
            await axios.post('http://localhost:8080/transacoes/realizar', {
                ...formData,
                valor: parseFloat(formData.valor)
            });
            setMessage({ type: 'success', text: 'Transferência realizada com sucesso!' });
            setFormData({ contaOrigem: '', contaDestino: '', valor: '' });
            if (onTransferComplete) onTransferComplete();
        } catch (error) {
            const errorMsg = error.response?.data?.message || 'Erro ao realizar transferência';
            setMessage({ type: 'error', text: errorMsg });
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="card">
            <div className="card-header">
                <ArrowRightLeft className="icon" />
                <h2>Transferência</h2>
            </div>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label> Conta de Origem</label>
                    <select
                        required
                        value={formData.contaOrigem}
                        onChange={(e) => setFormData({ ...formData, contaOrigem: e.target.value })}
                    >
                        <option value="">Selecione quem paga</option>
                        {users.map(u => (
                            <option key={u.id} value={u.numeroConta}>
                                {u.nome} (Saldo: R$ {u.saldo})
                            </option>
                        ))}
                    </select>
                </div>

                <div className="form-group">
                    <label>Conta de Destino</label>
                    <select
                        required
                        value={formData.contaDestino}
                        onChange={(e) => setFormData({ ...formData, contaDestino: e.target.value })}
                    >
                        <option value="">Selecione quem recebe</option>
                        {users.map(u => (
                            <option key={u.id} value={u.numeroConta}>
                                {u.nome} (Conta: {u.numeroConta})
                            </option>
                        ))}
                    </select>
                </div>

                <div className="form-group">
                    <label>Valor (R$)</label>
                    <input
                        type="number"
                        required
                        min="0.01"
                        step="0.01"
                        value={formData.valor}
                        onChange={(e) => setFormData({ ...formData, valor: e.target.value })}
                        placeholder="0,00"
                    />
                </div>

                {message && (
                    <div className={`message ${message.type}`}>
                        {message.text}
                    </div>
                )}

                <button type="submit" className="btn-primary" disabled={loading}>
                    {loading ? 'Processando...' : (
                        <>
                            <Send size={18} /> Transferir
                        </>
                    )}
                </button>
            </form>
        </div>
    );
}
