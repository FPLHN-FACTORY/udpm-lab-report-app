import { Button, Input, Modal, Select, message } from "antd";
import { useEffect, useState } from "react";
import { TeacherPointAPI } from "../../../../../api/teacher/point/TeacherPoint.api";
import { useParams } from "react-router";

const ModalAddHoney = ({ visible, onCancel, listCategory }) => {
  const [categoryId, setCategoryId] = useState("");
  const [errorCategory, setErrorCategory] = useState("");
  const { idClass } = useParams();
  useEffect(() => {
    if (visible) {
      setCategoryId("");
      setErrorCategory("");
    }
  }, [visible]);

  const addHoney = () => {
    let check = 0;
    if (categoryId === "") {
      setErrorCategory("Hãy chọn 1 loại mật ong");
      check++;
    } else {
      setErrorCategory("");
    }
    if (check === 0) {
      TeacherPointAPI.addHoney(idClass, categoryId).then(
        (response) => {
          message.success("Gửi yêu cầu cộng mật ong thành công");
        },
        (error) => {}
      );
    }
  };
  return (
    <>
      <Modal
        open={visible}
        onCancel={onCancel}
        bodyStyle={{ overflow: "hidden" }}
        footer={null}
      >
        <div
          style={{
            paddingTop: "0",
            borderBottom: "1px solid black",
          }}
        >
          <span style={{ fontSize: "18px" }}>Quy đổi điểm thưởng</span>
        </div>
        <div style={{ paddingTop: "20px" }}>
          <span>Danh sách thể loại mật ong:</span> <br />
          <Select
            value={categoryId}
            style={{ width: "100%" }}
            onChange={(e) => {
              setCategoryId(e);
            }}
          >
            <Select.Option value="">Chọn 1 loại mật ong</Select.Option>
            {listCategory != null && listCategory.map((item) => (
              <Select.Option value={item.id} key={item.id}>
                {item.name}
              </Select.Option>
            ))}
          </Select>{" "}
          <div>
            <span style={{ color: "red" }}>{errorCategory}</span>
          </div>
          <div style={{ marginTop: 8 }}>
            <span style={{ color: "red" }}>
              (*) Lưu ý: Danh sách sinh viên được gửi yêu cầu quy đổi mật ong là
              những sinh viên đã pass ở lớp xưởng thực hành.
            </span>
          </div>
          <div style={{ paddingTop: "15px", float: "right", right: 0 }}>
            <Button
              className="btn_clean"
              style={{
                marginRight: "5px",
              }}
              onClick={addHoney}
            >
              Gửi yêu cầu
            </Button>{" "}
            <Button
              className="btn_filter"
              style={{}}
              onClick={() => {
                onCancel();
              }}
            >
              Hủy
            </Button>
          </div>
        </div>
      </Modal>
    </>
  );
};
export default ModalAddHoney;
