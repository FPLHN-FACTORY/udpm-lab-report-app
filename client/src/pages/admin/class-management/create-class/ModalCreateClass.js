import React from "react";
import {
  Modal,
  Row,
  Col,
  Input,
  Button,
  Select,
  Space,
  Image,
  Tooltip,
  Table,
} from "antd";
import "./styleModalCreateProject.css";
import { useEffect, useState } from "react";
import moment from "moment";
// import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useAppDispatch } from "../../../../app/hook";
import LoadingIndicator from "../../../../helper/loading";
const { Option } = Select;

