import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [paymentMethods, setPaymentMethods] = useState([]);
  const [selectedMethod, setSelectedMethod] = useState('');
  const [amount, setAmount] = useState('');
  const [currency, setCurrency] = useState('USD');
  const [transactions, setTransactions] = useState([]);
  const [message, setMessage] = useState('');

  useEffect(() => {
    fetch('http://localhost:8080/api/payments/methods')
        .then(res => res.json())
        .then(data => {
          setPaymentMethods(data);
          setSelectedMethod(data[0]);
        });

    fetchTransactions();
  }, []);

  const fetchTransactions = () => {
    fetch('http://localhost:8080/api/payments/transactions')
        .then(res => res.json())
        .then(data => setTransactions(data));
  };

  const handlePay = () => {
    fetch('http://localhost:8080/api/payments/pay', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        paymentMethod: selectedMethod,
        amount: parseFloat(amount),
        currency: currency
      })
    })
        .then(res => res.json())
        .then(() => {
          setMessage('Ödeme başarılı!');
          setAmount('');
          fetchTransactions();
        })
        .catch(() => setMessage('Ödeme başarısız!'));
  };

  return (
      <div className="container">
        <h1>Ödeme Sistemi</h1>

        <div className="card">
          <h2>Yeni Ödeme</h2>
          <select value={selectedMethod} onChange={e => setSelectedMethod(e.target.value)}>
            {paymentMethods.map(method => (
                <option key={method} value={method}>{method}</option>
            ))}
          </select>
          <input
              type="number"
              placeholder="Tutar"
              value={amount}
              onChange={e => setAmount(e.target.value)}
          />
          <select value={currency} onChange={e => setCurrency(e.target.value)}>
            <option value="USD">USD</option>
            <option value="EUR">EUR</option>
            <option value="TRY">TRY</option>
          </select>
          <button onClick={handlePay}>Öde</button>
          {message && <p className="message">{message}</p>}
        </div>

        <div className="card">
          <h2>İşlem Geçmişi</h2>
          <table>
            <thead>
            <tr>
              <th>ID</th>
              <th>Yöntem</th>
              <th>Tutar</th>
              <th>Para Birimi</th>
              <th>Durum</th>
              <th>Tarih</th>
            </tr>
            </thead>
            <tbody>
            {transactions.map(t => (
                <tr key={t.id}>
                  <td>{t.id}</td>
                  <td>{t.paymentMethod}</td>
                  <td>{t.amount}</td>
                  <td>{t.currency}</td>
                    <td><span className="status-success">{t.status}</span></td>
                  <td>{new Date(t.createdAt).toLocaleString()}</td>
                </tr>
            ))}
            </tbody>
          </table>
        </div>
      </div>
  );
}

export default App;