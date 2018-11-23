***Hệ thống gồm 6 thành phần:
- Bank
- StockExchange (sàn giao dịch)
- Company
- Player
- Common (những interface, class dùng chung giữa 4 tp trên)
- Exception (xử lý lỗi)

Trong đó, Bank và StockExchange là Server (mỗi cái 1 instance); Player, Company là Client (nhiều instance). 4 cái chạy trên
các máy khác nhau.
Mỗi Server gồm các class: 
- *Manager: Quản lý mọi hoạt động của server
- *Controller: Các Object gửi về cho client để kết nối với server. Các controller liên kết với Manager để gọi hàm xử lý
  Cụ thể:
  + Bank gồm BankController (gửi cho StockExchange để thực hiện giao dịch của Player) và AccountController (gửi cho Player để
    quản lý tài khoản của họ)
  + StockExchange gồm PlayerStockController (gửi cho Player để giao dịch) và CompanyController (gửi cho Company)
- *Server: Chương trình chính, khởi động registry, gửi các controller theo tên miền
- Các class khác đặc trưng cho server, ví dụ bank có Account,...

Mỗi Client gồm:
- *Client: Khởi động registry, nhận các controller từ server
- Class xử lý  (Player, Company): Gọi các hàm của controller để xử lý thao tác của người dùng


Common gồm:
- Các Interface của *Controller: Các Client chỉ biết Interface của *Controller để đảm bảo họ k biết chi tiết hàm thực thi 
- Các Serializable Interface: Các Interface của class khác, không phải Controller nhưng vẫn cần truyền đi giữa Server và Client
 Ví dụ: Account, Stock (cổ phiếu)
- Convention: Các quy ước của trò chơi: Số tiền ban đầu, số người chơi tối đa,... và các tên miền của Server
- Utility: Các hàm công cụ: Tạo tên cho Computer Player

Exception:
Các lỗi có thể sinh ra trong quá trình xử lý: Đăng ký trùng tên, sai mật khẩu,...

***Luồng của Hệ thống:
1. Server tạo registry, Controller, gán tên miền cho controller
2. Client nhận registry, nhận controller qua registry 
3. Client dùng controller gửi các lệnh cho server: Register, Login, Buy, Sell,...
  Riêng hàm register gửi 1 instance của client đến server để nhận update sau này
4. Server nhận lệnh qua controller, gọi hàm của Manager để xử lý, trả kết quả hoặc throw Exception cho client
5. Client nhận kết quả, hiển thị
Ngoài ra, định kỳ Server gửi update về client (ví dụ: thay đổi giá cổ phiếu, tài khoản tăng do lãi suất) hoặc gửi update khi
có thay đổi xảy ra (ví dụ: có Player khác chấp nhận mua/bán cổ phiếu)

***Hoạt động giao dịch cổ phiếu:
- Player gửi Bid (gồm mã CP, số lượng) lên StockExchange
- StockExchange hiển thị các Bid, thông báo cho các Player khác và Company phát hành cổ phiếu đó 
- Player khác hoặc Company đồng ý mua/bán thì gửi yêu cầu chấp nhận lên StockExchange
- StockExchange gửi giao dịch qua Bank để cộng/trừ tiền các Player, xóa Bid
- Bank gửi thông báo thành công cho StockExchange, StockExchange thay đổi số cổ phiếu.
- Bank và StockExchange gửi thông báo thay đổi tài khoản/số cổ phiếu đến Player/Company tham gia.

***Thứ tự chạy chương trình:
- BankServer
- StockExchangeServer
- PlayerClient, CompanyClient

***Giao diện:
Giao diện 4 chương trình làm toàn bộ ở các file *Server, *Client
