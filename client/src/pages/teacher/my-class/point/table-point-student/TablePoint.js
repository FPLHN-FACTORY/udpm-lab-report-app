import { Input, Table } from "antd";
import { useAppDispatch, useAppSelector } from "../../../../../app/hook";
import {
  GetPoint,
  SetPoint,
} from "../../../../../app/teacher/point/tePointSlice.reduce";
import { useState } from "react";

const TablePoint = () => {
  const [phase1, setPhase1] = useState(0);
  const [phase2, setPhase2] = useState(0);
  const [final, setFinal] = useState(0);
  const dispatch = useAppDispatch();
  const dataSource = useAppSelector(GetPoint);

  const handlePoint = (phase1, phase2, final, record) => {
    const dataNew = dataSource.map((item1) => {
      if (
        item1.idClass === record.idClass &&
        item1.idStudent === record.idStudent
      ) {
        return {
          ...item1,
          checkPointPhase1: `${phase1}`,
          checkPointPhase2: `${phase2}`,
          finalPoint: `${final}`,
        };
      }
      return item1;
    });
    dispatch(SetPoint(dataNew));
  };

  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => <span>{index + 1}</span>,
    },
    {
      title: "Tên sinh viên",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
    },
    {
      title: "Điểm giai đoạn 1",
      dataIndex: "checkPointPhase1",
      key: "checkPointPhase1",
      render: (text, record) => {
        return (
          <>
            {" "}
            <Input
              placeholder="Nhập điểm"
              value={text}
              readOnly
              onDoubleClick={(e) => {
                e.target.readOnly = false;
                e.target.select();
              }}
              onBlur={(e) => {
                e.target.readOnly = true;
              }}
              onChange={(e) => {
                setPhase1(e.target.value);
                handlePoint(
                  e.target.value,
                  record.checkPointPhase2,
                  record.finalPoint,
                  record
                );
              }}
              type="number"
            />
          </>
        );
      },
    },
    {
      title: "Điểm giai đoạn 2",
      dataIndex: "checkPointPhase2",
      key: "checkPointPhase2",
      render: (text, record) => {
        return (
          <>
            {" "}
            <Input
              placeholder="Nhập điểm"
              value={text}
              onChange={(e) => {
                setPhase2(e.target.value);
                handlePoint(
                  record.checkPointPhase1,
                  e.target.value,
                  record.finalPoint,
                  record
                );
              }}
              type="number"
            />
          </>
        );
      },
    },
    {
      title: "Điểm final",
      dataIndex: "finalPoint",
      key: "finalPoint",
      render: (text, record) => {
        return (
          <>
            {" "}
            <Input
              placeholder="Nhập điểm"
              value={text}
              onChange={(e) => {
                setFinal(e.target.value);
                handlePoint(
                  record.checkPointPhase1,
                  record.checkPointPhase2,
                  e.target.value,
                  record
                );
              }}
              type="number"
            />
          </>
        );
      },
    },
  ];
  return (
    <>
      <Table
        rowKey="id"
        columns={columns}
        dataSource={dataSource}
        pagination={false}
      />
    </>
  );
};
export default TablePoint;
