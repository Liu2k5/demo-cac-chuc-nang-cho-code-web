import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import axios from "axios";

export default function Orders() {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);
    const [paymentUrl, setPaymentUrl] = useState("");
    const navigate = useNavigate();
    

    const handlePay = (id) => {
        axios.put("/api/customer/pay", id).then(data => setPaymentUrl(data.data))
        .finally(() => {
            if (paymentUrl) {
                navigate(paymentUrl);
            }
        });
        
    };

    useEffect(() => {
        axios.get("/api/customer/orders")
        .then(data => setOrders(data.data))
        .catch((e) => console.error("error fetching orders"))
        .finally(() => setLoading(false));
    }, []);

    if (loading) return (<div>Loading...</div>);

    return (
        <>
            <p>Danh sách đơn hàng</p>
            {orders.map((o) => (
                <p>{o.id} {o.totalAmount} <span><button onClick={handlePay(o.id)} disabled={o.paid} >Thanh toan</button></span></p>
            ))}
        </>
    );
}