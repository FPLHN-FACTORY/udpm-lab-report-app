import React, { useEffect, useState } from "react";
import { Modal, Row, Col, Input, Button, Select, message } from "antd";
import "react-toastify/dist/ReactToastify.css";
import { AdGroupProjectAPI } from "../../../../api/admin/AdGroupProjectAPI";

import { useAppDispatch } from "../../../../app/hook";
import { UpdateAdGroupProject } from "../../../../app/admin/AdGroupProjectSlice.reducer";
import LoadingIndicatorNoOverlay from "../../../../helper/loadingNoOverlay";

const { Option } = Select;

const ModalUpdateGroupProject = ({ visible, onCancel, item }) => {
  const [name, setName] = useState("");
  const [errorName, setErrorName] = useState("");
  const [descriptions, setDescriptions] = useState("");
  //   const [backgroundImage, setBackgroundImage] = useState("");
  const [selectedImageUrl, setSelectedImageUrl] = useState("");
  const [image, setImage] = useState([]);
  const [loading, setLoading] = useState(false);

  const dispatch = useAppDispatch();

  useEffect(() => {
    if (item != null) {
      setName(item.name);
      setDescriptions(item.descriptions);
      if (item.backgroundImage) {
        setSelectedImageUrl(item.backgroundImage);
      }
    }
    return () => {
      setName("");
      setDescriptions("");
      setSelectedImageUrl("");
      setImage([]);
      setErrorName("");
    };
  }, [item]);

  const handleFileInputChange = (event) => {
    const selectedFile = event.target.files[0];
    setImage(selectedFile);
    if (selectedFile) {
      const imageUrl = URL.createObjectURL(selectedFile);
      setSelectedImageUrl(imageUrl);
    } else {
      setSelectedImageUrl("");
    }
  };

  const updateGroupProject = () => {
    try {
      let check = 0;
      if (name.trim() === "") {
        setErrorName("Tên nhóm dự án không được để trống");
        check++;
      } else {
        setErrorName("");
      }
      if (check === 0) {
        let obj = {
          id: item.id,
          name: name,
          descriptions: descriptions,
          file: image,
        };
        setLoading(true);
        AdGroupProjectAPI.updateGroupProject(obj).then((response) => {
          dispatch(UpdateAdGroupProject(response.data.data));
          message.success("Cập nhật thành công !");
          setLoading(false);
          onCancel();
        });
      }
    } catch (error) {
      setLoading(false);
    }
  };

  return (
    <>
      {loading && <LoadingIndicatorNoOverlay />}
      <Modal
        visible={visible}
        onCancel={onCancel}
        width={800}
        footer={null}
        className="modal_show_detail_project"
      >
        <div>
          <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
            <span style={{ fontSize: "18px" }}>Cập nhật nhóm dự án</span>
          </div>
          <div
            style={{
              marginTop: "15px",
              borderBottom: "1px solid black",
              paddingBottom: 20,
            }}
          >
            <Row>
              <Col span={24}>
                Tên nhóm dự án:
                <Input
                  type="text"
                  placeholder="Nhập tên nhóm dự án"
                  value={name}
                  onChange={(e) => {
                    setName(e.target.value);
                  }}
                />{" "}
                <span style={{ color: "red" }}>{errorName}</span>
              </Col>
              <Col span={24} style={{ marginTop: 15 }}>
                Mô tả:
                <Input.TextArea
                  placeholder="Nhập mô tả"
                  value={descriptions}
                  onChange={(e) => {
                    setDescriptions(e.target.value);
                  }}
                  rows={5}
                />
              </Col>
              <Col style={{ marginTop: 15 }} span={24}>
                Ảnh:
                <Input
                  className="hidden-input"
                  id="select-avatar"
                  type="file"
                  accept="image/*"
                  onChange={(event) => handleFileInputChange(event)}
                />
                {selectedImageUrl !== "" && (
                  <div
                    onClick={() => {
                      document.getElementById("select-avatar").click();
                    }}
                    className="image-container"
                    style={{ marginTop: 10 }}
                  >
                    {<img src={selectedImageUrl} alt="Ảnh" width="30%" />}
                  </div>
                )}
              </Col>
            </Row>
          </div>

          <div style={{ textAlign: "right" }}>
            <div style={{ paddingTop: "15px" }}>
              <Button
                style={{
                  marginRight: "5px",
                  backgroundColor: "rgb(61, 139, 227)",
                  color: "white",
                }}
                onClick={updateGroupProject}
              >
                Cập nhật
              </Button>
              <Button
                style={{
                  backgroundColor: "red",
                  color: "white",
                }}
                onClick={onCancel}
              >
                Hủy
              </Button>
            </div>
          </div>
        </div>
      </Modal>
    </>
  );
};

export default ModalUpdateGroupProject;
