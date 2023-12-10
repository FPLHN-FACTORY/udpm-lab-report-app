import { Modal, Row, Col, Input, Button, message } from "antd";
import { useEffect, useState } from "react";
import { AdCategoryAPI } from "../../../../api/admin-category/adCategory.api";

import "react-toastify/dist/ReactToastify.css";
import { useAppDispatch } from "../../../../app/hook";
import { UpdateCategory } from "../../../../app/reducer/admin/category-management/adCategorySlice.reducer";

const ModalUpdateCategory = ({ visible, onCancel, category }) => {
  const [name, setName] = useState("");
  const [code, setCode] = useState("");
  const [errorName, setErrorName] = useState("");
  const dispatch = useAppDispatch();

  useEffect(() => {
    if (category !== null && visible) {
      setName(category.name);
      return () => {
        setName("");
        setErrorName("");
      };
    }
    if (!visible) {
      setName("");
      setErrorName("");
    }
  }, [visible]);

  const update = () => {
    let check = 0;
    if (name.trim() === "") {
      setErrorName("Tên thể loại không được để trống");
      check++;
    } else if (name.trim().length > 100) {
      setErrorName("Tên thể loại không được > 100 ký tự");
      check++;
    } else {
      setErrorName("");
    }

    if (check === 0) {
      let cate = {
        id: category.id,
        code: code,
        name: name,
      };

      AdCategoryAPI.update(category.id, cate).then(
        (response) => {
          message.success("Cập nhật thành công !");
          dispatch(UpdateCategory(response.data.data));
          onCancel();
        },
        (error) => {}
      );
    }
  };

  return (
    <>
      <Modal visible={visible} onCancel={onCancel} width={500} footer={null}>
        {" "}
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>Cập nhật thể loại</span>
        </div>
        <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={24}>
              <span>Tên thể loại:</span> <br />
              <Input
                value={name}
                onChange={(e) => {
                  setName(e.target.value);
                }}
                type="text"
              />
              <span className="error">{errorName}</span>
            </Col>
          </Row>
        </div>
        <div style={{ textAlign: "right" }}>
          <div style={{ paddingTop: "15px" }}>
            <Button
              className="btn_filter"
              style={{
                backgroundColor: "red",
                color: "white",
                width: "86px",
              }}
              onClick={onCancel}
            >
              Hủy
            </Button>{" "}
            <Button
              className="btn_clean"
              style={{ width: "86px", marginLeft: "10px" }}
              onClick={update}
            >
              Cập nhật
            </Button>
          </div>
        </div>
      </Modal>
    </>
  );
};

export default ModalUpdateCategory;
