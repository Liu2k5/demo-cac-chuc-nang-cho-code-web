import logo from './logo.svg';
import './App.css';
import { Link } from 'react-router-dom';

function App() {
  return (
      <div className="App">
        <header className="App-header">
          <h1>Index page</h1>
            <p>manage-customer: <Link  to="/admin/manage-customer">na na na</Link></p>
        </header>
      </div>
  );
}

export default App;
