import { useState, useEffect } from "react";
import axios from "axios";

export default function ManageCustomer() {
    const [customers, setCustomers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [loadingMore, setLoadingMore] = useState(false);
    const [hasMore, setHasMore] = useState(true);
    const [currentPage, setCurrentPage] = useState(0);
    const PAGE_SIZE = 5;
    const [conversation, setConversation] = useState([]);
    const [message, setMessage] = useState("");
    const [sending, setSending] = useState(false);
    const [chatError, setChatError] = useState("");

    // Tải trang đầu tiên
    const handleRefresh = async () => {
        setLoading(true);
        setCurrentPage(0);
        try {
            const response = await axios.get("/api/admin/manage-customer", {
                params: { page: 0, size: PAGE_SIZE, sortBy: "username" }
            });
            setCustomers(response.data.customers);
            setHasMore(response.data.hasMore);
        } catch (error) {
            console.error("Lỗi khi lấy dữ liệu:", error);
        } finally {
            setLoading(false);
        }
    };

    // Tải thêm trang tiếp theo
    const handleLoadMore = async () => {
        const nextPage = currentPage + 1;
        setLoadingMore(true);
        try {
            const response = await axios.get("/api/admin/manage-customer", {
                params: { page: nextPage, size: PAGE_SIZE, sortBy: "username" }
            });
            setCustomers(prev => [...prev, ...response.data.customers]);
            setHasMore(response.data.hasMore);
            setCurrentPage(nextPage);
        } catch (error) {
            console.error("Lỗi khi tải thêm dữ liệu:", error);
        } finally {
            setLoadingMore(false);
        }
    };

    const handleLoadConversation = async () => {
        try {
            const response = await axios.get("/api/admin/ai");
            setConversation(response.data);
        } catch (error) {
            console.log("Loi tai cuoc tro chuyen", error);
        }
    };

    useEffect(() => {
        handleRefresh();
        handleLoadConversation();
    }, []);

    const handleDelete = (event, username) => {
        event.preventDefault();
        axios.delete(`/api/admin/manage-customer/${username}`).then(handleRefresh)
            .catch(error => console.log(error));
    };

    const handleStatusChange = (event, username, isEnabled) => {
        event.preventDefault();
        const action = isEnabled ? "disable" : "enable";
        axios.put(`/api/admin/manage-customer/${username}/${action}`).then(handleRefresh)
            .catch(error => console.log(error));
    };

    const handleSendMessage = async (event) => {
        event.preventDefault();
        const trimmedMessage = message.trim();

        if (!trimmedMessage || sending) {
            return;
        }

        setSending(true);
        setChatError("");

        try {
            const response = await axios.post("/api/admin/ai", {
                question: trimmedMessage,
            });
            setConversation(response.data);
            setMessage("");
        } catch (error) {
            const responseMessage = error?.response?.data?.message || "Không gửi được tin nhắn";
            setChatError(responseMessage);
        } finally {
            setSending(false);
        }
    };

    return (
        <div style={{ maxWidth: 960, margin: "0 auto", padding: "24px 16px 40px" }}>
            <h1>Danh sách khách hàng</h1>
            {loading ? (
                <p>Đang tải dữ liệu...</p>
            ) : (
                <>
                <ul>
                    {customers.length > 0 ? (
                        customers.map((customer, index) => (
                            <li key={index} style={{ marginBottom: 12 }}>
                                {customer.username} - {customer.emailAddress}
                                <form onSubmit={(event) => handleDelete(event, customer.username)} style={{ display: "inline", marginLeft: 12 }}>
                                    <button type="submit">Xóa</button>
                                </form>
                                <form onSubmit={(event) => handleStatusChange(event, customer.username, customer.enabled)} style={{ display: "inline", marginLeft: 8 }}>
                                    {customer.enabled ? (
                                        <button type="submit">Khóa</button>
                                    ) : (
                                        <button type="submit">Mở</button>
                                    )}
                                </form>
                            </li>
                        ))
                    ) : (
                        <p>Không có dữ liệu</p>
                    )}
                </ul>
                    {hasMore ? (
                        <div style={{ textAlign: "center", marginTop: 12 }}>
                            <button
                                onClick={handleLoadMore}
                                disabled={loadingMore}
                                style={{
                                    border: "none",
                                    borderRadius: 999,
                                    padding: "8px 16px",
                                    background: loadingMore ? "#93c5fd" : "#1d4ed8",
                                    color: "white",
                                    cursor: loadingMore ? "not-allowed" : "pointer",
                                }}
                            >
                                {loadingMore ? "Đang tải..." : "Xem thêm"}
                            </button>
                        </div>
                    ) : customers.length > 0 ? (
                        <p style={{ textAlign: "center", color: "#6b7280", marginTop: 12 }}>Đã hiển thị tất cả</p>
                    ) : null}
                </>
            )}

            <br />
            <h1>AI chatbot</h1>
            <div
                style={{
                    minHeight: 420,
                    width: "100%",
                    overflowY: "auto",
                    border: "1px solid #d1d5db",
                    borderRadius: 16,
                    background: "#f9fafb",
                    padding: 16,
                    marginBottom: 12,
                }}
            >
                {conversation.length === 0 ? (
                    <p style={{ color: "#6b7280", margin: 0 }}>Hãy gửi câu hỏi đầu tiên để bắt đầu cuộc trò chuyện.</p>
                ) : (
                    conversation.map((item, index) => (
                        <div
                            key={`${item.author}-${index}`}
                            style={{
                                display: "flex",
                                justifyContent: item.author === "user" ? "flex-end" : "flex-start",
                                marginBottom: 12,
                            }}
                        >
                            <div
                                style={{
                                    maxWidth: "80%",
                                    padding: "12px 14px",
                                    borderRadius: 16,
                                    background: item.author === "user" ? "#1d4ed8" : "#ffffff",
                                    color: item.author === "user" ? "#ffffff" : "#111827",
                                    boxShadow: "0 1px 3px rgba(0, 0, 0, 0.08)",
                                    whiteSpace: "pre-wrap",
                                }}
                            >
                                <div style={{ fontSize: 12, opacity: 0.7, marginBottom: 4 }}>
                                    {item.author === "user" ? "Bạn" : "AI"}
                                </div>
                                <div>{item.content}</div>
                            </div>
                        </div>
                    ))
                )}
            </div>

            <form onSubmit={handleSendMessage} style={{ display: "grid", gap: 12 }}>
                <textarea
                    value={message}
                    onChange={(event) => setMessage(event.target.value)}
                    placeholder="Nhập câu hỏi cho AI..."
                    rows={3}
                    style={{
                        width: "100%",
                        resize: "vertical",
                        borderRadius: 12,
                        border: "1px solid #d1d5db",
                        padding: 12,
                        fontSize: 16,
                        lineHeight: 1.5,
                    }}
                />
                {chatError ? <p style={{ color: "#b91c1c", margin: 0 }}>{chatError}</p> : null}
                <div style={{ display: "flex", justifyContent: "flex-end" }}>
                    <button
                        type="submit"
                        disabled={sending}
                        style={{
                            border: "none",
                            borderRadius: 999,
                            padding: "10px 18px",
                            background: sending ? "#93c5fd" : "#1d4ed8",
                            color: "white",
                            cursor: sending ? "not-allowed" : "pointer",
                        }}
                    >
                        {sending ? "Đang gửi..." : "Gửi"}
                    </button>
                </div>
            </form>
        </div>
    );
}