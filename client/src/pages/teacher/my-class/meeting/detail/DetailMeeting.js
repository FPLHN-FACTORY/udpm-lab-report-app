import { Modal, Row, Col, Input, Table, Select } from "antd";
import { useParams } from "react-router";
import "./styleDetailMeeting.css";
import { useEffect, useState } from "react";
import { useAppSelector } from "../../../../../app/hook";
import moment from "moment";

const DetailMeeting = () => {
  const { idClass, idMeeting } = useParams();

  return (
    <>
      <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
        <span style={{ fontSize: "18px" }}>Chi tiết nhóm</span>
      </div>
      <br /> <br /> <br /> <br />
      detail class {idClass} va idMetting {idMeeting}
    </>
  );
};

export default DetailMeeting;
