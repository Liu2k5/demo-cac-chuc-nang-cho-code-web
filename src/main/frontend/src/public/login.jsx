import axios from "axios";
import {useState} from "react";
import {useNavigate} from "react-router";

export default function Login() {
    const [error, setError] = useState(null);
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();
    const handleSubmit = (e) => {
        e.preventDefault();
        const params = new URLSearchParams();
        params.append("username", username);
        params.append("password", password);
        axios.post("/login", params).then(() => navigate("/"))
            // hứng lỗi trả về từ backend, từ loginFailureHandler()
            .catch((error) => {
                if (error.response && error.response.data) {
                    setError(error.response.data.error);
                } else {
                    setError(error.toString());
                }
            });
    }

    return (
        <>
            <form onSubmit={handleSubmit}>
                <p style={{color:"red"}}>{error}</p>
                <div>
                    <label htmlFor="username">Tên đăng nhập:</label>
                    <input type="text" id="username" name="username" required
                           onChange={(e) => setUsername(e.target.value)}
                    />
                </div>
                <div>
                    <label htmlFor="password">Mật khẩu:</label>
                    <input type="password" id="password" name="password" required
                           onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                <button type="submit">Đăng nhập</button>
            </form>
        </>
    );
}